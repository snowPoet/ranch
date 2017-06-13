package org.lpw.ranch.account;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.lpw.ranch.account.log.LogModel;
import org.lpw.ranch.account.type.AccountTypes;

import javax.inject.Inject;

/**
 * @author lpw
 */
public class RewardTest extends TestSupport {
    @Inject
    private AccountTypes accountTypes;

    @Test
    public void profit() {
        validate("reward", 7, true);

        mockUser();
        mockHelper.reset();
        mockHelper.getRequest().addParameter("user", "user 1");
        mockHelper.getRequest().addParameter("owner", "owner 1");
        mockHelper.getRequest().addParameter("amount", "1");
        mockHelper.getRequest().addParameter("label", "label 1");
        sign.put(mockHelper.getRequest().getMap(), null);
        mockHelper.mock("/account/reward");
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        JSONObject data = object.getJSONObject("data");
        Assert.assertEquals(12, data.size());
        Assert.assertEquals("user 1", data.getString("user"));
        Assert.assertEquals("owner 1", data.getString("owner"));
        for (String property : new String[]{"type", "deposit", "withdraw", "profit", "consume", "pending"})
            Assert.assertEquals(0, data.getIntValue(property));
        for (String property : new String[]{"balance", "reward"})
            Assert.assertEquals(1, data.getIntValue(property));
        AccountModel account = liteOrm.findById(AccountModel.class, data.getString("id"));
        Assert.assertEquals("user 1", account.getUser());
        Assert.assertEquals("owner 1", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(1, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(1, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(0, account.getConsume());
        Assert.assertEquals(0, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&user 1&owner 1&0&1&0&0&1&0&0&0"), account.getChecksum());
        LogModel log = liteOrm.findById(LogModel.class, data.getString("logId"));
        Assert.assertEquals("user 1", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("reward", log.getType());
        Assert.assertEquals(1, log.getAmount());
        Assert.assertEquals(1, log.getBalance());
        Assert.assertEquals(3, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() < 2000L);
        Assert.assertNull(log.getEnd());
        Assert.assertNull(log.getJson());

        mockUser();
        mockCarousel.register("ranch.user.sign", "{\"code\":0,\"data\":{\"id\":\"sign in id\"}}");
        mockHelper.reset();
        mockHelper.getRequest().addParameter("owner", "owner 2");
        mockHelper.getRequest().addParameter("amount", "2");
        mockHelper.getRequest().addParameter("label", "label 2");
        sign.put(mockHelper.getRequest().getMap(), null);
        mockHelper.mock("/account/reward");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals(12, data.size());
        Assert.assertEquals("sign in id", data.getString("user"));
        Assert.assertEquals("owner 2", data.getString("owner"));
        for (String property : new String[]{"type", "deposit", "withdraw", "profit", "consume", "pending"})
            Assert.assertEquals(0, data.getIntValue(property));
        for (String property : new String[]{"balance", "reward"})
            Assert.assertEquals(2, data.getIntValue(property));
        account = liteOrm.findById(AccountModel.class, data.getString("id"));
        Assert.assertEquals("sign in id", account.getUser());
        Assert.assertEquals("owner 2", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(2, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(2, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(0, account.getConsume());
        Assert.assertEquals(0, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&sign in id&owner 2&0&2&0&0&2&0&0&0"), account.getChecksum());
        log = liteOrm.findById(LogModel.class, data.getString("logId"));
        Assert.assertEquals("sign in id", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("reward", log.getType());
        Assert.assertEquals(2, log.getAmount());
        Assert.assertEquals(2, log.getBalance());
        Assert.assertEquals(3, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() < 2000L);
        Assert.assertNull(log.getEnd());
        Assert.assertNull(log.getJson());

        Assert.assertTrue(accountTypes.get(AccountTypes.REWARD).complete(null, null));
    }
}
