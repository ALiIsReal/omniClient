package com.tn.set.service.coin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.BigDecimalUtil;
import com.tn.set.service.util.HttpUtil;

/**
 * usdt操作
 *
 * @author cloud cloud
 * @create 2017/10/27
 **/
@Service
public class CoinUsdtService {
    

    private String address = "n2qnt1Qr3N5amBx4WvfcALf4nTb3Qj6WhQ";

    //propertyid 是在Omni layer注册的唯一标志

    private int propertyid = 31;


    private  String url = "http://127.0.0.1:18832";
    private  String username = "u";
    private  String password = "p";

    //private Logger log = Log.get();

    private final static String RESULT = "result";
    private final static String METHOD_SEND_TO_ADDRESS = "omni_send";
    private final static String METHOD_GET_TRANSACTION = "omni_gettransaction";
    private final static String METHOD_GET_BLOCK_COUNT = "getblockcount";
    private final static String METHOD_NEW_ADDRESS = "getnewaddress";
    private final static String METHOD_GET_BALANCE = "omni_getbalance";
    private final static String METHOD_GET_LISTBLOCKTRANSACTIONS = "omni_listblocktransactions";
    
 
    /*@Resource
    private IUserCoinService userCoinService;
    @Resource
    private ICoinParseService coinParseService;*/


    /***
     * 取得钱包相关信息
     * {
            "result": {
                "alerts": [],
                "mastercoreversion": "0.3.0",
                "totaltransactions": 0,
                "blocktime": 1379205517,
                "blocktransactions": 0,
                "totaltrades": 0,
                "omnicoreversion": "0.3.0",
                "bitcoincoreversion": "0.13.2",
                "omnicoreversion_int": 30000000,
                "block": 105194
            },
            "id": "1521096083469"
        }
     * 若获取失败，result为空，error信息为错误信息的编码
     * */
    public JSONObject getInfo() throws Exception {
        return doRequest("omni_getinfo");
    }
    
    /**
     * USDT产生地址
     * @return
     */
    public String getNewAddress(){
        JSONObject json = doRequest(METHOD_NEW_ADDRESS);
        if(isError(json)){
            //log.error("获取USDT地址失败:{}",json.get("error"));
            return "";
        }
        return json.getString(RESULT);
    }

    /**
     * USDT查询余额
     * @return
     */
    public long getBalance(){
        JSONObject json = doRequest(METHOD_GET_BALANCE,address,propertyid);
        if(isError(json)){
            //log.error("获取USDT余额:{}",json.get("error"));
        	System.out.println("获取USDT余额:{"+json.get("error")+"}");
            return 0;
        }
        System.out.println(json);
        return BigDecimalUtil.inputConvert(json.getJSONObject(RESULT).getDouble("balance"));
    }

    /**
     * USDT转帐
     * @param addr
     * @param value
     * @return
     */
    public String send(String addr,long value){
        if(vailedAddress(addr)){
            double amt = BigDecimalUtil.outputConvert(value);
            JSONObject json = doRequest(METHOD_SEND_TO_ADDRESS,address,addr,propertyid,amt);
            if(isError(json)){
                //log.error("USDT 转帐给{} value:{}  失败 ：",addr,amt,json.get("error"));
                return "";
            }else{
                //log.info("USDT 转币给{} value:{} 成功",addr,amt);
                return json.getString(RESULT);
            }
        }else{
            //log.error("USDT接受地址不正确");
            return "";
        }
    }

    /**
     * 验证地址的有效性
     * @param address
     * @return
     * @throws Exception
     */
    public boolean vailedAddress(String address) {
        JSONObject json  = doRequest("validateaddress",address);
        if(isError(json)){
            //log.error("USDT验证地址失败:",json.get("error"));
            return false;
        }else{
            return json.getJSONObject(RESULT).getBoolean("isvalid");
        }
    }


    /**
     * 区块高度
     * @return
     */
    public int getBlockCount(){
        JSONObject json = null;
        try {
            json = doRequest(METHOD_GET_BLOCK_COUNT);
            if(!isError(json)){
                return json.getInteger("result");
            }else{
                //log.error(json.toString());
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*public boolean parseBlock(int index) {
        //doRequest("omni_listblocktransactions",279007);
        //{"result":["63d7e22de0cf4c0b7fd60b4b2c9f4b4b781f7fdb8be4bcaed870a8b407b90cf1","6fb25ab84189d136b95d7f733b0659fa5fbd63f476fb1bca340fb4f93de6c912","d54213046d8be80c44258230dd3689da11fdcda5b167f7d10c4f169bd23d1c01"],"id":"1521454868826"}
        JSONObject jsonBlock = doRequest(METHOD_GET_LISTBLOCKTRANSACTIONS, index);
        if (isError(jsonBlock)) {
            //log.error("访问USDT出错");
            return false;
        }
        
        JSONArray jsonArrayTx = jsonBlock.getJSONArray(RESULT);
        if (jsonArrayTx == null || jsonArrayTx.size() == 0) {
            //没有交易
            return true;
        }
        List<UserCoinAddressEntity> userList = userCoinService.getAllUserCoinAddress(CoinConstant.COIN_USDT);
        if(userList == null || userList.size() == 0){
            return true;
        }

        Iterator<Object> iteratorTxs = jsonArrayTx.iterator();
        while(iteratorTxs.hasNext()){
            String txid = (String) iteratorTxs.next();
            parseTx(txid,userList);
        }
        return true;
        
    }

    public void parseTx(String txid,List<UserCoinAddressEntity> userList){
        *//**
         * {"result":{"amount":"0.10000000","ecosystem":"test","propertyname":"Bazz",
         * "data":"BazzyFoo","divisible":true,"fee":"0.00010000","propertytype":"divisible",
         * "txid":"63d7e22de0cf4c0b7fd60b4b2c9f4b4b781f7fdb8be4bcaed870a8b407b90cf1",
         * "ismine":false,"type":"Create Property - Manual","confirmations":453133,
         * "version":0,"url":"www.bazzcoin.info","sendingaddress":"2N1WnASsjgwrzbucBHhMd6gHLqpipq7kUZM",
         * "valid":true,"blockhash":"000000002101ea0da161b6a63f4d1a0e37a2bd58c5aee49a3b8fd80640b09662",
         * "blocktime":1409941113,"positioninblock":19,"block":279007,"category":"FooMANAGED",
         * "subcategory":"Bar","propertyid":2147483664,"type_int":54},"id":"1521456310559"}
         *//*
        JSONObject jsonTransaction = doRequest(METHOD_GET_TRANSACTION, txid);
        if(isError(jsonTransaction)) {
            log.error("处理USDT tx出错");
            return;
        }
        JSONObject jsonTResult = jsonTransaction.getJSONObject(RESULT);
        if (!jsonTResult.getBoolean("valid")) {
            log.info("不是有效数据");
            return;
        }

        int propertyidResult = jsonTResult.getIntValue("propertyid");
        if (propertyidResult!=propertyid) {
            log.info("非USDT数据");
            return;
        }

        int coinfirm = jsonTResult.getIntValue("confirmations");
        double value = jsonTResult.getDouble("amount");
        if(value >0) {
            String address = jsonTResult.getString("referenceaddress");
            for (UserCoinAddressEntity addressModel : userList) {
                //如果有地址是分配给用记的地址， 则说明用户在充值
                if (address.equals(addressModel.getAddress())) {
                    //添加充值记录
                    UserCoinRecordEntity record = new UserCoinRecordEntity();
                    record.setCoinType(CoinConstant.COIN_USDT);
                    record.setAddress(address);
                    record.setComfirmCount(coinfirm);
                    record.setOpType(CoinConstant.OP_RECHAREG);
                    record.setStatus(CommonConstant.STATUS_1);
                    record.setTxid(txid);
                    record.setValue(BigDecimalUtil.inputConvert(value));
                    record.setCreateTm(new Date());
                    record.setUserId(addressModel.getUserId());
                    try {
                        rechargeParse(record);
                    }catch (DuplicateKeyException e){
                        log.error("这个用户{}的充值已经处理了 币：{}",addressModel.getUserId(),CoinConstant.COIN_USDT);
                    }
                }
            }
        }
    }

    private void rechargeParse(UserCoinRecordEntity recordEntity){
        if(!userCoinService.createUserCoinRecord(recordEntity)){
            log.error("添加记录失败");
            return;
        }
        log.info("用户：{} 充值：{}  coinType:{}", recordEntity.getUserId(), recordEntity.getValue(), recordEntity.getCoinType());
        UserCoinEntity userCoin = userCoinService.getUserCoinByUserIdAndCoin(recordEntity.getUserId(), recordEntity.getCoinType());
        if (userCoin == null || StringUtils.isEmpty(userCoin.getCoinType())) {
            userCoin = new UserCoinEntity();
            userCoin.setUserId(recordEntity.getUserId());
            userCoin.setValue(recordEntity.getValue());
            userCoin.setCoinType(recordEntity.getCoinType());
            userCoin.setVersion(0);
            userCoin.setLockValue(0);
            userCoinService.insertUserCoin(userCoin,recordEntity.getValue(),TradeConstrant.TRADE_TYPE_RECHARGE,TradeConstrant.TRADE_TYPE_RECHARGE_DESC+BigDecimalUtil.outputConvert(recordEntity.getValue())+userCoin.getCoinType());
        } else {
            userCoin.setValue(BigDecimalUtil.longAdd(userCoin.getValue(), recordEntity.getValue()));
            userCoinService.updateUserCoin(userCoin,recordEntity.getValue(),TradeConstrant.TRADE_TYPE_RECHARGE,TradeConstrant.TRADE_TYPE_RECHARGE_DESC+BigDecimalUtil.outputConvert(recordEntity.getValue())+userCoin.getCoinType());
        }
    }*/


    private boolean isError(JSONObject json){
        if( json == null || (StringUtils.isNotEmpty(json.getString("error")) && json.get("error") != "null")){
            return true;
        }
        return false;
    }

    

    private JSONObject doRequest(String method,Object... params){
        JSONObject param = new JSONObject();
        param.put("id",System.currentTimeMillis()+"");
        param.put("jsonrpc","2.0");
        param.put("method",method);
        if(params != null){
            param.put("params",params);
        }
        String creb = Base64.encodeBase64String((username+":"+password).getBytes());
        Map<String,String> headers = new HashMap<String, String>(2);
        headers.put("Authorization","Basic "+creb);
        return JSON.parseObject(HttpUtil.jsonPost(url,headers,param.toJSONString()));
    }

    public static void main(String args[]) throws Exception{
        CoinUsdtService usdtUtils = new CoinUsdtService();
//        JSONObject count = usdtUtils.getInfo();
//        System.out.println(count);
        //System.out.println(usdtUtils.parseBlock(279007));
    }

}



