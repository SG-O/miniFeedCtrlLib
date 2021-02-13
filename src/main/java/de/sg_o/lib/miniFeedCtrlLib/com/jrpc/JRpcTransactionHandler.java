package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.Request;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JRpcTransactionHandler extends TransactionHandler {

    public Request generateRequest(Method method) {
        return new JRpcRequest(super.getAndIncCounter(), method);
    }

    @Override
    public Request generateRequest(int id, Method method) {
        return new JRpcRequest(id, method);
    }

    public Transaction parseResponse(byte[] msg) {
        try {
            if (msg == null) throw new NullPointerException();
            String msgStrg = new String(msg, StandardCharsets.ISO_8859_1);
            JSONObject responseObject = new JSONObject(msgStrg);
            int id = responseObject.optInt("id", -1);
            if (id < 1) return null;
            JRpcResponse response = new JRpcResponse(responseObject);
            Transaction t = super.removeTransaction(id);
            t.setResponse(response);
            return t;
        } catch (NullPointerException | JSONException ignore){
        }
        return null;
    }
}
