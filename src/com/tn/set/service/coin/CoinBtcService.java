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
 * btc����
 *
 * @author cloud cloud
 * @create 2017/10/27
 **/
@Service
public class CoinBtcService {

	// �������Լ�Ǯ����ַ �� ���õ�rpc�û�������

	// private String url = "http://127.0.0.1:18332";
	private String url = "http://127.0.0.1:18832";

	private String username = "u";
	private String password = "p";

	// private Logger log = Log.get();// ��־

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

	// ��Ŀҵ��service������Ҫ���
	// @Resource
	// private IUserCoinService userCoinService;
	// @Resource
	// private ICoinParseService coinParseService;

	/***
	 * ȡ��Ǯ�������Ϣ
	 * {"result":{"version":80300,"protocolversion":70001,"walletversion":60000,
	 * "balance":0.00009500,"blocks":284795,"timeoffset":-1,"connections":6,
	 * "proxy":"","difficulty":2621404453.06461525,"testnet":false,
	 * "keypoololdest":1388131357,"keypoolsize":102,"paytxfee":0.00000000,
	 * "errors":""},"error":null,"id":1} ����ȡʧ�ܣ�resultΪ�գ�error��ϢΪ������Ϣ�ı���
	 * */
	public JSONObject getInfo() throws Exception {
		return doRequest("getinfo");
	}

	public JSONObject getHelp() throws Exception {
		return doRequest("help");
	}

	/**
	 * ��ȡ������Ϣ
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
	 * ���������Ϣ
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
	 * ��ý�����Ϣ
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
	 * ���Ǯ����Ϣ
	 */
	public JSONObject getWalletInfo(){
		return doRequest(METHOD_GET_WALLETINFO);
	}
	/**
	 * BTC������ַ
	 * 
	 * @return
	 */
	public String getNewAddress() {
		JSONObject json = doRequest(METHOD_NEW_ADDRESS);
		if (isError(json)) {
			// log.error("��ȡBTC��ַʧ��:{}", json.get("error"));
			System.out.println("��ȡBTC��ַʧ��:{}" + json.get("error"));
			return "";
		}
		return json.getString(RESULT);
	}

	/**
	 * BTC��ѯ���
	 * 
	 * @return
	 */
	public long getBalance() {
		JSONObject json = doRequest(METHOD_GET_BALANCE);
		if (isError(json)) {
			// log.error("��ȡBTC���:{}", json.get("error"));
			System.out.println("��ȡBTC���:{" + json.get("error") + "}");
			return 0;
		}
		return BigDecimalUtil.inputConvert(json.getDouble(RESULT));
	}

	/**
	 * BTCת��
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
				// log.error("BTC ת�ʸ�{} value:{}  ʧ�� ��", addr, amt,
				// json.get("error"));
				System.out.println("BTC ת�ʸ�{" + addr + "} value:{" + amt
						+ "}  ʧ�� ��" + json.get("error"));
				return "";
			} else {
				// log.info("BTC ת�Ҹ�{} value:{} �ɹ�", addr, amt);
				System.out.println("BTC ת�Ҹ�{" + addr + "} value:{" + amt
						+ "} �ɹ�");
				return json.getString(RESULT);
			}
		} else {
			// log.error("BTC���ܵ�ַ����ȷ");
			return "";
		}
	}

	/**
	 * ��֤��ַ����Ч��
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean vailedAddress(String address) {
		JSONObject json = doRequest("validateaddress", address);
		if (isError(json)) {
			// log.error("BTC��֤��ַʧ��:", json.get("error"));
			System.out.println("BTC��֤��ַʧ��:" + json.get("error"));
			return false;
		} else {
			return json.getJSONObject(RESULT).getBoolean("isvalid");
		}
	}

	/**
	 * ����߶�
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
//            log.error("����BTC����");
//            return false;
//        }
//        String hash = jsonBlockHash.getString(RESULT);
//        JSONObject jsonBlock = doRequest(METHOD_GET_BLOCK, hash);
//        if (isError(jsonBlock)) {
//            log.error("����BTC����");
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
//                //û�н���
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
//            log.error("����BTC tx����");
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
//                    //����е�ַ�Ƿ�����üǵĵ�ַ�� ��˵���û��ڳ�ֵ
//                    if (address.equals(addressModel.getAddress())) {
//                        //��ӳ�ֵ��¼
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
//                            log.error("����û�{}�ĳ�ֵ�Ѿ������� �ң�{}",addressModel.getUserId(),CoinConstant.COIN_BTC);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void rechargeParse(UserCoinRecordEntity recordEntity){
//        if(!userCoinService.createUserCoinRecord(recordEntity)){
//            log.error("��Ӽ�¼ʧ��");
//            return;
//        }
//        log.info("�û���{} ��ֵ��{}  coinType:{}", recordEntity.getUserId(), recordEntity.getValue(), recordEntity.getCoinType());
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
