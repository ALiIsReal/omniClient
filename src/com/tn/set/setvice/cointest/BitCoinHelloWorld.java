package com.tn.set.setvice.cointest;



import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

public class BitCoinHelloWorld implements WalletCoinsReceivedEventListener {

	@Override
	public void onCoinsReceived(Wallet wallet, Transaction transaction,
			Coin prevBalance, Coin newBalance) {
		// TODO Auto-generated method stub

	}

	public void run() {
		try {
			init();
			System.out.println("Waiting for coins...");
			while (true) {
				Thread.sleep(20);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(){
		//NetworkParameters params = TestNet3Params.get();
		NetworkParameters params = MainNetParams.get();
		ECKey key = new ECKey();
		System.out.println("We create a new key:\n "+key);
		Address addressFromKey = key.toAddress(params);
		System.out.println("Public Address generated: "+addressFromKey);
		
		System.out.println("Private key is: "+key.getPrivateKeyAsWiF(params).toString());
		
		//Wallet wallet = new Wallet(params);
		//wallet.importKey(key);
	}
	
	
	public static void main(String[] args) {
		BitCoinHelloWorld demo = new BitCoinHelloWorld();
		demo.run();
	}

}
