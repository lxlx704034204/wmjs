import {fetch,convertJson2Url,customParam,save} from "../../../common/fetch/fetch";
import {SERVER_URL} from "../../../common/constant/serverConfig";


module.exports ={

    query(queryParams) {

        if(!queryParams){
            queryParams = {};
        }
        queryParams.fetchProperties = "*,memberMemorial.*";

        const url= SERVER_URL + "api/memorialobituary/query?" + convertJson2Url(queryParams);

        return fetch(url).then(function(response){
            return response.json();
        });
    },
    save(params){

        if(!params.pkMemorialObituary){
            params["version"] = 0;
        }

        return save(SERVER_URL + "api/memorialobituary/save",{
            body: customParam(params)
        });
    },
    deleteItem(pkMemorialObituary){
        const url= SERVER_URL + "api/memorialobituary/"+pkMemorialObituary+"/delete";
        return fetch(url).then(function(response){
            if(response){
                return response.json();
            }
        });
    }
};