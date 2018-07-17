package com.buyfull.openapiv1;

import com.buyfull.openapiv1.implement.BFInstallSite_Implement;
import com.buyfull.openapiv1.implement.BFObjFactory;
import com.buyfull.openapiv1.implement.BFOpenAPI_Implement;
import org.json.JSONException;

import java.text.ParseException;

import static com.buyfull.openapiv1.BFOpenAPIManager.createBFOpenAPInstance;

/**
 * ClassName INstallTest
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/29  18:33
 * @VERSION 1.0
 **/


public class INstallTest {

    public static void main(String[] args) throws ParseException, BFException, JSONException {

        BFOpenAPI_Implement manager = (BFOpenAPI_Implement) createBFOpenAPInstance(
                "AKID2L9NbuU67cJa0B88b656nbb1l1Tb144PvTeA",
                "6dU8stjbx361ezqjbH6g630sozhge16yy0NdVv6A"  );

        BFInstallSite_Implement  installSite_implement = BFObjFactory.createBFInstallSite(manager ,"7c9021df24b646d39363c1d581c10021"    ) ;
        installSite_implement.unbindDeviceSN();
        installSite_implement.bindDeviceSN("2018051113685735");

    }
}
