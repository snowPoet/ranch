package org.lpw.ranch.address;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.lpw.tephra.ctrl.validate.Validators;
import org.lpw.tephra.util.TimeUnit;

/**
 * @author lpw
 */
public class SaveTest extends TestSupport {
    @Test
    public void save() {
        AddressModel address1 = create("user 1", 1, 0);

        mockCarousel.reset();
        mockCarousel.register("ranch.user.sign", "{\"code\":0,\"data\":{}}");
        mockHelper.reset();
        mockHelper.mock("/address/save");
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2102, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "illegal-id", message.get(AddressModel.NAME + ".region")), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", "region id");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2102, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "illegal-id", message.get(AddressModel.NAME + ".region")), object.getString("message"));

        String region = generator.uuid();
        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2103, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "empty", message.get(AddressModel.NAME + ".detail")), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", generator.random(101));
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2104, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(AddressModel.NAME + ".detail"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("postcode", generator.random(101));
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2105, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(AddressModel.NAME + ".postcode"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("name", generator.random(101));
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2112, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(AddressModel.NAME + ".name"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("phone", generator.random(101));
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2113, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(AddressModel.NAME + ".phone"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("latitude", generator.random(101));
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2106, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(AddressModel.NAME + ".latitude"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("latitude", "123.456");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2107, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "illegal-latitude", message.get(AddressModel.NAME + ".latitude")), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("longitude", generator.random(101));
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2108, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(AddressModel.NAME + ".longitude"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("longitude", "-123.456");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2109, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "illegal-longitude", message.get(AddressModel.NAME + ".longitude")), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("label", generator.random(101));
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2110, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(AddressModel.NAME + ".label"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("major", "-1");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2111, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "not-between", message.get(AddressModel.NAME + ".major"), 0, 1), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("major", "2");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2111, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "not-between", message.get(AddressModel.NAME + ".major"), 0, 1), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(9901, object.getIntValue("code"));
        Assert.assertEquals(message.get("ranch.user.helper.need-sign-in"), object.getString("message"));

        mockCarousel.register("ranch.user.sign", "{\"code\":0,\"data\":{\"id\":\"sign in id\"}}");
        mockHelper.reset();
        mockHelper.getRequest().addParameter("id", address1.getId());
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2101, object.getIntValue("code"));
        Assert.assertEquals(message.get(AddressModel.NAME + ".update.disabled"), object.getString("message"));

        mockCarousel.register("ranch.user.sign", "{\"code\":0,\"data\":{\"id\":\"user 1\"}}");
        mockHelper.reset();
        mockHelper.getRequest().addParameter("id", address1.getId());
        mockHelper.getRequest().addParameter("region", region);
        mockHelper.getRequest().addParameter("detail", "detail value");
        mockHelper.getRequest().addParameter("postcode", "postcode value");
        mockHelper.getRequest().addParameter("name", "name value");
        mockHelper.getRequest().addParameter("phone", "phone value");
        mockHelper.getRequest().addParameter("latitude", "-12.345678");
        mockHelper.getRequest().addParameter("longitude", "12.345678");
        mockHelper.getRequest().addParameter("label", "label value");
        mockHelper.getRequest().addParameter("major", "1");
        mockHelper.getRequest().addParameter("time", "2000-01-02 03:04:05");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        JSONObject data = object.getJSONObject("data");
        Assert.assertEquals(address1.getId(), data.getString("id"));
        Assert.assertEquals("user 1", data.getString("user"));
        Assert.assertEquals(region, data.getJSONObject("region").getString("id"));
        Assert.assertEquals("detail value", data.getString("detail"));
        Assert.assertEquals("postcode value", data.getString("postcode"));
        Assert.assertEquals("name value", data.getString("name"));
        Assert.assertEquals("phone value", data.getString("phone"));
        Assert.assertEquals("-12.345678", data.getString("latitude"));
        Assert.assertEquals("12.345678", data.getString("longitude"));
        Assert.assertEquals("label value", data.getString("label"));
        Assert.assertEquals(1, data.getIntValue("major"));
        Assert.assertTrue(System.currentTimeMillis() - dateTime.toTime(data.getString("time")).getTime() < 2000);
        AddressModel address11 = liteOrm.findById(AddressModel.class, address1.getId());
        Assert.assertEquals("user 1", address11.getUser());
        Assert.assertEquals(region, address11.getRegion());
        Assert.assertEquals("detail value", address11.getDetail());
        Assert.assertEquals("postcode value", address11.getPostcode());
        Assert.assertEquals("name value", address11.getName());
        Assert.assertEquals("phone value", address11.getPhone());
        Assert.assertEquals("-12.345678", address11.getLatitude());
        Assert.assertEquals("12.345678", address11.getLongitude());
        Assert.assertEquals("label value", address11.getLabel());
        Assert.assertEquals(1, address11.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address11.getTime().getTime() < 2000);

        thread.sleep(3, TimeUnit.Second);
        String region2 = generator.uuid();
        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region2);
        mockHelper.getRequest().addParameter("detail", "detail value 2");
        mockHelper.getRequest().addParameter("postcode", "postcode value 2");
        mockHelper.getRequest().addParameter("name", "name value 2");
        mockHelper.getRequest().addParameter("phone", "phone value 2");
        mockHelper.getRequest().addParameter("latitude", "-12.3456789");
        mockHelper.getRequest().addParameter("longitude", "12.3456789");
        mockHelper.getRequest().addParameter("label", "label value 2");
        mockHelper.getRequest().addParameter("major", "1");
        mockHelper.getRequest().addParameter("time", "2002-01-02 03:04:05");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals("user 1", data.getString("user"));
        Assert.assertEquals(region2, data.getJSONObject("region").getString("id"));
        Assert.assertEquals("detail value 2", data.getString("detail"));
        Assert.assertEquals("postcode value 2", data.getString("postcode"));
        Assert.assertEquals("name value 2", data.getString("name"));
        Assert.assertEquals("phone value 2", data.getString("phone"));
        Assert.assertEquals("-12.3456789", data.getString("latitude"));
        Assert.assertEquals("12.3456789", data.getString("longitude"));
        Assert.assertEquals("label value 2", data.getString("label"));
        Assert.assertEquals(1, data.getIntValue("major"));
        Assert.assertTrue(System.currentTimeMillis() - dateTime.toTime(data.getString("time")).getTime() < 2000);
        AddressModel address2 = liteOrm.findById(AddressModel.class, data.getString("id"));
        Assert.assertEquals("user 1", address2.getUser());
        Assert.assertEquals(region2, address2.getRegion());
        Assert.assertEquals("detail value 2", address2.getDetail());
        Assert.assertEquals("postcode value 2", address2.getPostcode());
        Assert.assertEquals("name value 2", address2.getName());
        Assert.assertEquals("phone value 2", address2.getPhone());
        Assert.assertEquals("-12.3456789", address2.getLatitude());
        Assert.assertEquals("12.3456789", address2.getLongitude());
        Assert.assertEquals("label value 2", address2.getLabel());
        Assert.assertEquals(1, address2.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address2.getTime().getTime() < 2000);
        AddressModel address111 = liteOrm.findById(AddressModel.class, address1.getId());
        Assert.assertEquals("user 1", address111.getUser());
        Assert.assertEquals(region, address111.getRegion());
        Assert.assertEquals("detail value", address111.getDetail());
        Assert.assertEquals("postcode value", address111.getPostcode());
        Assert.assertEquals("name value", address111.getName());
        Assert.assertEquals("phone value", address111.getPhone());
        Assert.assertEquals("-12.345678", address111.getLatitude());
        Assert.assertEquals("12.345678", address111.getLongitude());
        Assert.assertEquals("label value", address111.getLabel());
        Assert.assertEquals(0, address111.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address111.getTime().getTime() > 2000);

        mockCarousel.register("ranch.user.sign", "{\"code\":0,\"data\":{\"id\":\"user 3\"}}");
        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region2);
        mockHelper.getRequest().addParameter("detail", "detail value 3");
        mockHelper.getRequest().addParameter("postcode", "postcode value 3");
        mockHelper.getRequest().addParameter("name", "name value 3");
        mockHelper.getRequest().addParameter("phone", "phone value 3");
        mockHelper.getRequest().addParameter("latitude", "-12.34567890");
        mockHelper.getRequest().addParameter("longitude", "12.34567890");
        mockHelper.getRequest().addParameter("label", "label value 3");
        mockHelper.getRequest().addParameter("major", "1");
        mockHelper.getRequest().addParameter("time", "2003-01-02 03:04:05");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals("user 3", data.getString("user"));
        Assert.assertEquals(region2, data.getJSONObject("region").getString("id"));
        Assert.assertEquals("detail value 3", data.getString("detail"));
        Assert.assertEquals("postcode value 3", data.getString("postcode"));
        Assert.assertEquals("name value 3", data.getString("name"));
        Assert.assertEquals("phone value 3", data.getString("phone"));
        Assert.assertEquals("-12.34567890", data.getString("latitude"));
        Assert.assertEquals("12.34567890", data.getString("longitude"));
        Assert.assertEquals("label value 3", data.getString("label"));
        Assert.assertEquals(1, data.getIntValue("major"));
        Assert.assertTrue(System.currentTimeMillis() - dateTime.toTime(data.getString("time")).getTime() < 2000);
        AddressModel address3 = liteOrm.findById(AddressModel.class, data.getString("id"));
        Assert.assertEquals("user 3", address3.getUser());
        Assert.assertEquals(region2, address3.getRegion());
        Assert.assertEquals("detail value 3", address3.getDetail());
        Assert.assertEquals("postcode value 3", address3.getPostcode());
        Assert.assertEquals("name value 3", address3.getName());
        Assert.assertEquals("phone value 3", address3.getPhone());
        Assert.assertEquals("-12.34567890", address3.getLatitude());
        Assert.assertEquals("12.34567890", address3.getLongitude());
        Assert.assertEquals("label value 3", address3.getLabel());
        Assert.assertEquals(1, address3.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address3.getTime().getTime() < 2000);
        AddressModel address22 = liteOrm.findById(AddressModel.class, address2.getId());
        Assert.assertEquals("user 1", address22.getUser());
        Assert.assertEquals(region2, address22.getRegion());
        Assert.assertEquals("detail value 2", address22.getDetail());
        Assert.assertEquals("postcode value 2", address22.getPostcode());
        Assert.assertEquals("name value 2", address22.getName());
        Assert.assertEquals("phone value 2", address22.getPhone());
        Assert.assertEquals("-12.3456789", address22.getLatitude());
        Assert.assertEquals("12.3456789", address22.getLongitude());
        Assert.assertEquals("label value 2", address22.getLabel());
        Assert.assertEquals(1, address22.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address2.getTime().getTime() < 2000);
        AddressModel address1111 = liteOrm.findById(AddressModel.class, address1.getId());
        Assert.assertEquals("user 1", address1111.getUser());
        Assert.assertEquals(region, address1111.getRegion());
        Assert.assertEquals("detail value", address1111.getDetail());
        Assert.assertEquals("postcode value", address1111.getPostcode());
        Assert.assertEquals("name value", address1111.getName());
        Assert.assertEquals("phone value", address1111.getPhone());
        Assert.assertEquals("-12.345678", address1111.getLatitude());
        Assert.assertEquals("12.345678", address1111.getLongitude());
        Assert.assertEquals("label value", address1111.getLabel());
        Assert.assertEquals(0, address1111.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address1111.getTime().getTime() > 2000);

        mockHelper.reset();
        mockHelper.getRequest().addParameter("region", region2);
        mockHelper.getRequest().addParameter("detail", "detail value 33");
        mockHelper.getRequest().addParameter("postcode", "postcode value 33");
        mockHelper.getRequest().addParameter("name", "name value 33");
        mockHelper.getRequest().addParameter("phone", "phone value 33");
        mockHelper.getRequest().addParameter("latitude", "-12.345678903");
        mockHelper.getRequest().addParameter("longitude", "12.345678903");
        mockHelper.getRequest().addParameter("label", "label value 33");
        mockHelper.getRequest().addParameter("major", "0");
        mockHelper.getRequest().addParameter("time", "2003-01-02 03:04:33");
        mockHelper.mock("/address/save");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals("user 3", data.getString("user"));
        Assert.assertEquals(region2, data.getJSONObject("region").getString("id"));
        Assert.assertEquals("detail value 33", data.getString("detail"));
        Assert.assertEquals("postcode value 33", data.getString("postcode"));
        Assert.assertEquals("name value 33", data.getString("name"));
        Assert.assertEquals("phone value 33", data.getString("phone"));
        Assert.assertEquals("-12.345678903", data.getString("latitude"));
        Assert.assertEquals("12.345678903", data.getString("longitude"));
        Assert.assertEquals("label value 33", data.getString("label"));
        Assert.assertEquals(0, data.getIntValue("major"));
        Assert.assertTrue(System.currentTimeMillis() - dateTime.toTime(data.getString("time")).getTime() < 2000);
        AddressModel address33 = liteOrm.findById(AddressModel.class, address3.getId());
        Assert.assertEquals("user 3", address33.getUser());
        Assert.assertEquals(region2, address33.getRegion());
        Assert.assertEquals("detail value 3", address33.getDetail());
        Assert.assertEquals("postcode value 3", address33.getPostcode());
        Assert.assertEquals("name value 3", address33.getName());
        Assert.assertEquals("phone value 3", address33.getPhone());
        Assert.assertEquals("-12.34567890", address33.getLatitude());
        Assert.assertEquals("12.34567890", address33.getLongitude());
        Assert.assertEquals("label value 3", address33.getLabel());
        Assert.assertEquals(1, address33.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address33.getTime().getTime() < 2000);
        AddressModel address333 = liteOrm.findById(AddressModel.class, data.getString("id"));
        Assert.assertEquals("user 3", address333.getUser());
        Assert.assertEquals(region2, address333.getRegion());
        Assert.assertEquals("detail value 33", address333.getDetail());
        Assert.assertEquals("postcode value 33", address333.getPostcode());
        Assert.assertEquals("name value 33", address333.getName());
        Assert.assertEquals("phone value 33", address333.getPhone());
        Assert.assertEquals("-12.345678903", address333.getLatitude());
        Assert.assertEquals("12.345678903", address333.getLongitude());
        Assert.assertEquals("label value 33", address333.getLabel());
        Assert.assertEquals(0, address333.getMajor());
        Assert.assertTrue(System.currentTimeMillis() - address333.getTime().getTime() < 2000);
    }
}
