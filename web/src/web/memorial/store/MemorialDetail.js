import {fetch,convertJson2Url,customParam,save} from "../../../common/fetch/fetch";
import {SERVER_URL} from "../../../common/constant/serverConfig";


module.exports ={

    query(queryParams) {

        if(!queryParams){
            queryParams = {};
        }
        queryParams.fetchProperties = "*,memberMemorial.*,memberMemorial.personalInfo.name,commonUser.name";

        const url= SERVER_URL + "api/memorialdetail/query?" + convertJson2Url(queryParams);

        return fetch(url).then(function(response){
            return response.json();
        });
    },
    memorialQuery(queryParams){

        if(!queryParams){
            queryParams = {};
        }
        queryParams.fetchProperties = "*,personalInfo.name";

        const url= SERVER_URL + "api/membermemorial/query?" + convertJson2Url(queryParams);

        return fetch(url).then(function(response){
            return response.json();
        });
    },
    saveMemorial(params){
        params.fetchProperties = "*,personalInfo.name";
        return save(SERVER_URL + "api/membermemorial/save",{
            body: customParam(params)
        });
    },
    saveGoods(params){

        if(!params.pkMemorialDetail){
            params["version"] = 0;
        }
        params["fetchProperties"] = "*,memberMemorial.*,memberMemorial.personalInfo.name,commonUser.name";

        return save(SERVER_URL + "api/memorialdetail/savedetail",{
            body: customParam(params)
        });
    },
    clearGoods(params){
        const url= SERVER_URL + "api/memorialdetail/clear?" + convertJson2Url(params);
        return fetch(url).then(function(response){
            return response.json();
        });
    }

};