import {fetch,convertJson2Url} from "../../../common/fetch/fetch";
import {SERVER_URL} from "../../../common/constant/serverConfig";


module.exports = function (queryParams) {

    queryParams.fetchProperties = "*,commonUser.*,personalInfo.*";

    const url= SERVER_URL + "api/generaluser/query?" + convertJson2Url(queryParams);
    return fetch(url).then(function(response){
        if(response){
            return response.json();
        }
    });
};
