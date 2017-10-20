ios app支付
必传两个参数 certificate,acountId
 支付订单 http://192.168.7.111:8082/bookOrder/payment
 参数(post : json)    {"acountId":1000,"bookOrderDetailList":[{"billingMode":1,"payType":1,"type":4}]}
返回{"code":200,"msg":"成功","list":
"{\"acountId\":1000,\"bookOrderDetailList\":[{\"billingMode\":1,\"bookOrderDetailId\":0,\"bookOrderId\":0,\"createDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950187,\"timezoneOffset\":-480,\"year\":117},\"endDate\":{\"date\":20,\"day\":1,\"hours\":16,\"minutes\":42,\"month\":10,\"seconds\":30,\"time\":1511167350187,\"timezoneOffset\":-480,\"year\":117},\"money\":0,\"payType\":1,\"realMoney\":12,\"startDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950187,\"timezoneOffset\":-480,\"year\":117},\"status\":0,\"type\":4,\"updateDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950187,\"timezoneOffset\":-480,\"year\":117}}],\"bookOrderId\":0,\"createDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950188,\"timezoneOffset\":-480,\"year\":117},\"orderNumber\":\"85152017102016423010005\",\"updateDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950188,\"timezoneOffset\":-480,\"year\":117}}"
}

支付验证回调 http://192.168.7.111:8082/bookOrder/iospayNotifyUrl
 参数(post : json)   
 {"body":"sdfsdfsdf2fdsfd",
 "bookOrder":
 "{\"acountId\":1000,\"bookOrderDetailList\":[{\"billingMode\":1,\"bookOrderDetailId\":0,\"bookOrderId\":0,\"createDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950187,\"timezoneOffset\":-480,\"year\":117},\"endDate\":{\"date\":20,\"day\":1,\"hours\":16,\"minutes\":42,\"month\":10,\"seconds\":30,\"time\":1511167350187,\"timezoneOffset\":-480,\"year\":117},\"money\":0,\"payType\":1,\"realMoney\":12,\"startDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950187,\"timezoneOffset\":-480,\"year\":117},\"status\":0,\"type\":4,\"updateDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950187,\"timezoneOffset\":-480,\"year\":117}}],\"bookOrderId\":0,\"createDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950188,\"timezoneOffset\":-480,\"year\":117},\"orderNumber\":\"85152017102016423010005\",\"updateDate\":{\"date\":20,\"day\":5,\"hours\":16,\"minutes\":42,\"month\":9,\"seconds\":30,\"time\":1508488950188,\"timezoneOffset\":-480,\"year\":117}}"
 }
返回{"status":0,XXXXXX}

