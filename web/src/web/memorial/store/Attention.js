import {fetch,convertJson2Url,customParam,save} from "../../../common/fetch/fetch";
import {SERVER_URL} from "../../../common/constant/serverConfig";


module.exports ={

    query(queryParams) {

        if(!queryParams){
            queryParams = {};
        }
        queryParams.fetchProperties = "*,memberMemorial.*,memberMemorial.personalInfo.*";

        const url= SERVER_URL + "api/carememorial/query?" + convertJson2Url(queryParams);

        return fetch(url).then(function(response){
            return response.json();
        });
    },
    cancel(pkCareMemorial){

        return fetch(SERVER_URL + "api/carememorial/" + pkCareMemorial + "/delete").then(function(response){
            return response.json();
        });
    },
    save(params){

        if(!params.pkCareMemorial){
            params["version"] = 0;
        }

        return save(SERVER_URL + "api/carememorial/save",{
            body: customParam(params)
        });
    }

};