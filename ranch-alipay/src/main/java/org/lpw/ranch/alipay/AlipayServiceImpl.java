package org.lpw.ranch.alipay;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import org.lpw.ranch.payment.helper.PaymentHelper;
import org.lpw.ranch.user.helper.UserHelper;
import org.lpw.tephra.dao.model.ModelHelper;
import org.lpw.tephra.util.Converter;
import org.lpw.tephra.util.Logger;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author lpw
 */
@Service(AlipayModel.NAME + ".service")
public class AlipayServiceImpl implements AlipayService {
    @Inject
    private Validator validator;
    @Inject
    private Converter converter;
    @Inject
    private Logger logger;
    @Inject
    private ModelHelper modelHelper;
    @Inject
    private UserHelper userHelper;
    @Inject
    private PaymentHelper paymentHelper;
    @Inject
    private AlipayDao alipayDao;
    @Value("${tephra.ctrl.service-root:}")
    private String root;

    @Override
    public JSONArray query() {
        return modelHelper.toJson(alipayDao.query().getList());
    }

    @Override
    public AlipayModel findByKey(String key) {
        return alipayDao.findByKey(key);
    }

    @Override
    public AlipayModel findByAppId(String appId) {
        return alipayDao.findByAppId(appId);
    }

    @Override
    public void save(AlipayModel alipay) {
        AlipayModel model = alipayDao.findByKey(alipay.getKey());
        if (model == null) {
            model = new AlipayModel();
            model.setKey(alipay.getKey());
        }
        model.setName(alipay.getName());
        model.setUrl(alipay.getUrl());
        model.setAppId(alipay.getAppId());
        model.setPrivateKey(alipay.getPrivateKey());
        model.setPublicKey(alipay.getPublicKey());
        alipayDao.save(model);
    }

    @Override
    public String quickWapPay(String key, String user, String subject, int amount, String notifyUrl, String returnUrl) {
        if (validator.isEmpty(user))
            user = userHelper.id();
        String orderNo = paymentHelper.create("alipay", user, amount, notifyUrl);
        if (validator.isEmpty(orderNo))
            return null;

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderNo);
        model.setSubject(subject);
        model.setTotalAmount(converter.toString(amount * 0.01D, "0.00"));
        model.setProductCode("QUICK_WAP_PAY");
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(root + "/alipay/notify");
        request.setReturnUrl(returnUrl);

        try {
            return newAlipayClient(alipayDao.findByKey(key)).pageExecute(request).getBody();
        } catch (Throwable e) {
            logger.warn(e, "执行支付宝WAP付款时发生异常！");

            return null;
        }
    }

    private AlipayClient newAlipayClient(AlipayModel alipay) {
        return new DefaultAlipayClient(alipay.getUrl(), alipay.getAppId(), alipay.getPrivateKey(),
                "json", "UTF-8", alipay.getPublicKey(), "RSA2");
    }

    @Override
    public boolean notify(String appId, String orderNo, String tradeNo, String amount, String status, String signType, Map<String, String> map) {
        if (status.equals("WAIT_BUYER_PAY"))
            return false;

        AlipayModel alipay = alipayDao.findByAppId(appId);
        if (alipay == null)
            return false;

        try {
            AlipaySignature.checkSignAndDecrypt(map, alipay.getPublicKey(), alipay.getPrivateKey(), true, false, signType);
        } catch (AlipayApiException e) {
            logger.warn(e, "验证支付宝异步通知签名失败！");

            return false;
        }

        int state = status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED") ? 1 : 0;

        return orderNo.equals(paymentHelper.complete(orderNo, getAmount(amount), tradeNo, state));
    }

    private int getAmount(String amount) {
        int indexOf = amount.indexOf('.');
        if (indexOf == -1)
            return converter.toInt(amount) * 100;

        return converter.toInt(amount.substring(0, indexOf)) * 100 + converter.toInt(amount.substring(indexOf + 1)) % 100;
    }
}