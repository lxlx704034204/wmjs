import {fetch,convertJson2Url} from "../fetch/fetch";
import {SERVER_URL} from "../constant/serverConfig";


module.exports = function(queryParams) {

    if(!queryParams){
        queryParams = {};
    }
    queryParams.fetchProperties = "factKey,displayName";

    const url= SERVER_URL + "api/dictionaryitem/query?" + convertJson2Url(queryParams);

    return fetch(url).then(function(response){
        return response.json();
    });
};