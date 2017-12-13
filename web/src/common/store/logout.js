import {fetch,convertJson2Url} from "../fetch/fetch";
import {SERVER_URL} from "../constant/serverConfig";


module.exports = function() {
    const url= SERVER_URL + "j_spring_security_logout";
    return fetch(url);
};