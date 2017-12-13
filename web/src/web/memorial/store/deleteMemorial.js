import {fetch,convertJson2Url} from "../../../common/fetch/fetch";
import {SERVER_URL} from "../../../common/constant/serverConfig";


module.exports = function (pkMemberMemorial) {
    const url= SERVER_URL + "api/membermemorial/"+pkMemberMemorial+"/delete";
    return fetch(url).then(function(response){
        if(response){
            return response.json();
        }
    });
};
