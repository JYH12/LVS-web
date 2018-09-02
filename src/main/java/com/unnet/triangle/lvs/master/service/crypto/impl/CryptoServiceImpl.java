package com.unnet.triangle.lvs.master.service.crypto.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.service.crypto.CryptoService;
import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.utils.crypto.CryptoUtil;
@Service
public class CryptoServiceImpl implements CryptoService {

	 @Override
	    public String encrypt(String data) {
	        try {
	            String timestamp = System.currentTimeMillis() + "000";
	            String encryptedData = CryptoUtil.encrpytAESCBCBase64(data, timestamp);
	            String finalData = merge(encryptedData, timestamp);
	            return CryptoUtil.encrpytAESCBCBase64(finalData, Constants.ENCRYPT_KEY);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    @Override
	    public String decrypt(String data) {
	        try {
	            String c = CryptoUtil.decryptAESCBCFromBase64(data, Constants.ENCRYPT_KEY);
	            Map<String, String> map = separete(c);
	            return CryptoUtil.decryptAESCBCFromBase64(map.get("a"), map.get("b"));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * merge
	     * 
	     * @param a length of a must >= 24
	     * @param b length of b must = 24
	     * @return
	     */
	    private String merge(String a, String b) {
	        return a + "`" + b;
	    }

	    /**
	     * separate into a and b
	     * 
	     * @param c
	     * @return a map contains key of
	     * 
	     *         <pre>
	     * a, b
	     *         </pre>
	     */
	    private Map<String, String> separete(String c) {
	        String[] seps = c.split("`");
	        Map<String, String> map = new HashMap<>();
	        map.put("a", seps[0]);
	        map.put("b", seps[1]);
	        return map;
	    }

}
