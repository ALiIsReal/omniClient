package com.tn.set.service.coin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tn.set.service.util.BigDecimalUtil;
import com.tn.set.service.util.HttpUtil;

/**
 * btc操作
 *
 * @author cloud cloud
 * @create 2017/10/27
 **/
@Service
public class CoinBtcService {

	// 这里是自己钱包地址 和 设置的rpc用户名密码

	// private String url = "http://127.0.0.1:18332";
	private String url = "http://127.0.0.1:18832";

	private String username = "u";
	private String password = "p";

	// private Logger log = Log.get();// 日志

	private final static String RESULT = "result";
	private final static String METHOD_SEND_TO_ADDRESS = "sendtoaddress";
	private final static String METHOD_GET_BLOCK = "getblock";
	private final static String METHOD_GET_BLOCK_HASH = "getblockhash";
	private final static String METHOD_GET_TRANSACTION = "gettransaction";
	private final static String METHOD_GET_RAW_TRANSACTION = "getrawtransaction";
	private final static String METHOD_GET_BLOCK_COUNT = "getblockcount";
	private final static String METHOD_NEW_ADDRESS = "getnewaddress";
	private final static String METHOD_GET_BALANCE = "getbalance";
	private final static String METHOD_GET_WALLETINFO = "getwalletinfo";
	private final static String METHOD_HELP = "help";
	private final static int MIN_CONFIRMATION = 6;

	// 项目业务service，不需要理会
	// @Resource
	// private IUserCoinService userCoinService;
	// @Resource
	// private ICoinParseService coinParseService;

	/***
	 * 取得钱包相关信息
	 * {"result":{"version":80300,"protocolversion":70001,"walletversion":60000,
	 * "balance":0.00009500,"blocks":284795,"timeoffset":-1,"connections":6,
	 * "proxy":"","difficulty":2621404453.06461525,"testnet":false,
	 * "keypoololdest":1388131357,"keypoolsize":102,"paytxfee":0.00000000,
	 * "errors":""},"error":null,"id":1} 若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject getInfo() throws Exception {
		return doRequest("getinfo");
	}

	public JSONObject getHelp() throws Exception {
		return doRequest("help");
	}

	/**
	 * 获取块链信息
	 * 
	 * @return 
	 *         {"result":{"headers":511570,"chain":"main","size_on_disk":4182213,
	 *         "mediantime"
	 *         :1242927192,"blocks":15241,"pruned":false,"warnings":"",
	 *         "difficulty"
	 *         :1,"bip9_softforks":{"segwit":{"startTime":1479168000,
	 *         "timeout":1510704000
	 *         ,"status":"defined","since":0},"csv":{"startTime":1462060800,
	 *         "timeout":1493596800,"status":"defined","since":0}}, "chainwork":
	 *         "00000000000000000000000000000000000000000000000000003b8a3b8a3b8a"
	 *         ,
	 *         "verificationprogress":0.0000501980096031837,"softforks":[{"reject"
	 *         :{"status":false},
	 *         "id":"bip34","version":2},{"reject":{"status":false
	 *         },"id":"bip66","version":3},
	 *         {"reject":{"status":false},"id":"bip65","version":4}],
	 *         "bestblockhash":
	 *         "00000000787bb5153d76225b438596b3fc01cf8a27beec9c9fb48c20b911edfc"
	 *         , "initialblockdownload":true},"id":1}
	 * @throws Exception
	 */
	public JSONObject getBlockChainInfo() throws Exception {
		return doRequest("getblockchaininfo");
	}
	/**
	 * 获得区块信息
	 */
	public JSONObject getBlock(int index){
		JSONObject jsonBlockHash = doRequest(METHOD_GET_BLOCK_HASH, index);
		if (isError(jsonBlockHash)) {
          return null;
      }
      String hash = jsonBlockHash.getString(RESULT);
      JSONObject jsonBlock = doRequest(METHOD_GET_BLOCK, hash);
      if (isError(jsonBlock)) {
         return null;
      }
		return jsonBlock;
	}
	/**
	 * 获得交易信息
	 */
	public JSONObject getTransaction(String tranHash){
		JSONObject json = doRequest(METHOD_GET_TRANSACTION, tranHash);
		if(isError(json)){
			return null;
		}else {
			return json;
		}
		
				
	}
	/**
	 * 获得钱包信息
	 */
	public JSONObject getWalletInfo(){
		return doRequest(METHOD_GET_WALLETINFO);
	}
	/**
	 * BTC产生地址
	 * 
	 * @return
	 */
	public String getNewAddress() {
		JSONObject json = doRequest(METHOD_NEW_ADDRESS);
		if (isError(json)) {
			// log.error("获取BTC地址失败:{}", json.get("error"));
			System.out.println("获取BTC地址失败:{}" + json.get("error"));
			return "";
		}
		return json.getString(RESULT);
	}

	/**
	 * BTC查询余额
	 * 
	 * @return
	 */
	public long getBalance() {
		JSONObject json = doRequest(METHOD_GET_BALANCE);
		if (isError(json)) {
			// log.error("获取BTC余额:{}", json.get("error"));
			System.out.println("获取BTC余额:{" + json.get("error") + "}");
			return 0;
		}
		return BigDecimalUtil.inputConvert(json.getDouble(RESULT));
	}

	/**
	 * BTC转帐
	 * 
	 * @param addr
	 * @param value
	 * @return
	 */
	public String send(String addr, long value) {
		if (vailedAddress(addr)) {
			double amt = BigDecimalUtil.outputConvert(value);
			JSONObject json = doRequest(METHOD_SEND_TO_ADDRESS, addr, amt);
			if (isError(json)) {
				// log.error("BTC 转帐给{} value:{}  失败 ：", addr, amt,
				// json.get("error"));
				System.out.println("BTC 转帐给{" + addr + "} value:{" + amt
						+ "}  失败 ：" + json.get("error"));
				return "";
			} else {
				// log.info("BTC 转币给{} value:{} 成功", addr, amt);
				System.out.println("BTC 转币给{" + addr + "} value:{" + amt
						+ "} 成功");
				return json.getString(RESULT);
			}
		} else {
			// log.error("BTC接受地址不正确");
			return "";
		}
	}

	/**
	 * 验证地址的有效性
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean vailedAddress(String address) {
		JSONObject json = doRequest("validateaddress", address);
		if (isError(json)) {
			// log.error("BTC验证地址失败:", json.get("error"));
			System.out.println("BTC验证地址失败:" + json.get("error"));
			return false;
		} else {
			return json.getJSONObject(RESULT).getBoolean("isvalid");
		}
	}

	/**
	 * 区块高度
	 * 
	 * @return
	 */
	public int getBlockCount() {
		JSONObject json = null;
		try {
			json = doRequest(METHOD_GET_BLOCK_COUNT);
			if (!isError(json)) {
				return json.getInteger("result");
			} else {
				// log.error(json.toString());
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
//	public boolean parseBlock(int index) {
//        JSONObject jsonBlockHash = doRequest(METHOD_GET_BLOCK_HASH, index);
//        if (isError(jsonBlockHash)) {
//            log.error("访问BTC出错");
//            return false;
//        }
//        String hash = jsonBlockHash.getString(RESULT);
//        JSONObject jsonBlock = doRequest(METHOD_GET_BLOCK, hash);
//        if (isError(jsonBlock)) {
//            log.error("访问BTC出错");
//            return false;
//        }
//        JSONObject jsonBlockResult = jsonBlock.getJSONObject(RESULT);
//        int confirm = jsonBlockResult.getInteger("confirmations");
//        if (confirm >= MIN_CONFIRMATION) {
//            List<UserCoinAddressEntity> userList = userCoinService.getAllUserCoinAddress(CoinConstant.COIN_BTC);
//            if(userList == null || userList.size() == 0){
//                return true;
//            }
//            JSONArray jsonArrayTx = jsonBlockResult.getJSONArray("tx");
//            if (jsonArrayTx == null || jsonArrayTx.size() == 0) {
//                //没有交易
//                return true;
//            }
//            Iterator<Object> iteratorTxs = jsonArrayTx.iterator();
//            while(iteratorTxs.hasNext()){
//                String txid = (String) iteratorTxs.next();
//                parseTx(txid,confirm,userList);
//            }
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    public void parseTx(String txid,int coinfirm,List<UserCoinAddressEntity> userList){
//        JSONObject jsonTransaction = doRequest(METHOD_GET_TRANSACTION, txid);
//        if(isError(jsonTransaction)) {
//            log.error("处理BTC tx出错");
//            return;
//        }
//        JSONObject jsonTransactionResult = jsonTransaction.getJSONObject(RESULT);
//        JSONArray jsonArrayVout = jsonTransactionResult.getJSONArray("details");
//        if(jsonArrayVout == null || jsonArrayVout.size() == 0){
//            return;
//        }
//        Iterator<Object> iteratorVout = jsonArrayVout.iterator();
//        while (iteratorVout.hasNext()) {
//            JSONObject jsonVout = (JSONObject) iteratorVout.next();
//            double value = jsonVout.getDouble("amount");
//            String category = jsonVout.getString("category");
//            if(value >0&&"receive".equals(category)) {
//                String address = jsonVout.getString("address");
//                for (UserCoinAddressEntity addressModel : userList) {
//                    //如果有地址是分配给用记的地址， 则说明用户在充值
//                    if (address.equals(addressModel.getAddress())) {
//                        //添加充值记录
//                        UserCoinRecordEntity record = new UserCoinRecordEntity();
//                        record.setCoinType(CoinConstant.COIN_BTC);
//                        record.setAddress(address);
//                        record.setComfirmCount(coinfirm);
//                        record.setOpType(CoinConstant.OP_RECHAREG);
//                        record.setStatus(CommonConstant.STATUS_1);
//                        record.setTxid(txid);
//                        record.setValue(BigDecimalUtil.inputConvert(value));
//                        record.setCreateTm(new Date());
//                        record.setUserId(addressModel.getUserId());
//                        try {
//                            rechargeParse(record);
//                        }catch (DuplicateKeyException e){
//                            log.error("这个用户{}的充值已经处理了 币：{}",addressModel.getUserId(),CoinConstant.COIN_BTC);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void rechargeParse(UserCoinRecordEntity recordEntity){
//        if(!userCoinService.createUserCoinRecord(recordEntity)){
//            log.error("添加记录失败");
//            return;
//        }
//        log.info("用户：{} 充值：{}  coinType:{}", recordEntity.getUserId(), recordEntity.getValue(), recordEntity.getCoinType());
//        UserCoinEntity userCoin = userCoinService.getUserCoinByUserIdAndCoin(recordEntity.getUserId(), recordEntity.getCoinType());
//        if (userCoin == null || StringUtils.isEmpty(userCoin.getCoinType())) {
//            userCoin = new UserCoinEntity();
//            userCoin.setUserId(recordEntity.getUserId());
//            userCoin.setValue(recordEntity.getValue());
//            userCoin.setCoinType(recordEntity.getCoinType());
//            userCoin.setVersion(0);
//            userCoin.setLockValue(0);
//            userCoinService.insertUserCoin(userCoin,recordEntity.getValue(),TradeConstrant.TRADE_TYPE_RECHARGE,TradeConstrant.TRADE_TYPE_RECHARGE_DESC+BigDecimalUtil.outputConvert(recordEntity.getValue())+userCoin.getCoinType());
//        } else {
//            userCoin.setValue(BigDecimalUtil.longAdd(userCoin.getValue(), recordEntity.getValue()));
//            userCoinService.updateUserCoin(userCoin,recordEntity.getValue(),TradeConstrant.TRADE_TYPE_RECHARGE,TradeConstrant.TRADE_TYPE_RECHARGE_DESC+BigDecimalUtil.outputConvert(recordEntity.getValue())+userCoin.getCoinType());
//        }
//    }

	private boolean isError(JSONObject json) {
		if (json == null
				|| (StringUtils.isNotEmpty(json.getString("error")) && json
						.get("error") != "null")) {
			return true;
		}
		return false;
	}

	private JSONObject doRequest(String method, Object... params) {
		JSONObject param = new JSONObject();
		param.put("id", System.currentTimeMillis() + "");
		param.put("jsonrpc", "3.0");
		param.put("method", method);
		if (params != null) {
			param.put("params", params);
		}
		String creb = Base64.encodeBase64String((username + ":" + password)
				.getBytes());
		Map<String, String> headers = new HashMap<String, String>(2);
		headers.put("Authorization", "Basic " + creb);
		return JSON.parseObject(HttpUtil.jsonPost(url, headers,
				param.toJSONString()));
	}

	public static void main(String args[]) throws Exception {
		CoinBtcService btcUtils = new CoinBtcService();
		//JSONObject count = btcUtils.getTransaction("4c531f88925bdfd2c9fc1d48fad8a3b32aefc96e46bab4a529a3785c9ca532e5");
		//JSONObject count = btcUtils.getInfo();
		//JSONObject count = btcUtils.getBlock(btcUtils.getBlockCount());
		JSONObject count = btcUtils.getWalletInfo();
		System.out.println(count);
	}

}
