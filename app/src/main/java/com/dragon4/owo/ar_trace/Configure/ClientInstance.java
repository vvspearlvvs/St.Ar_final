package com.dragon4.owo.ar_trace.Configure;

import com.dragon4.owo.ar_trace.Network.ClientSelector;
import com.dragon4.owo.ar_trace.Network.Python.PythonClient;

public class ClientInstance {
    private static ClientSelector instanceClient;

    public ClientInstance(String selectedServer) {
    }

    public static ClientSelector getInstanceClient() {
        return instanceClient;
    }

    public static void setInstanceClient(String selectedServer) {
        if(selectedServer.equals("PYTHON")) {
            instanceClient = new PythonClient();
        }
        else if(selectedServer.equals("FIREBASE")) {
            //instanceClient = new FirebaseClient();
        }
    }

}