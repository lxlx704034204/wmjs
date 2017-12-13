import {fetch,convertJson2Url} from "../fetch/fetch";
import {SERVER_URL} from "../constant/serverConfig";


module.exports = function(queryParams) {

    if(!queryParams){
        queryParams = {};
        queryParams.fetchProperties = "*,organization.name," +
            "organization.registerAddress.fullName," +
            "organization.street,organization.type.*," +
            "servicePoint.pkServicePoint," +
            "servicePoint.version,servicePoint.name," +
            "privateOrganizations.name,privateOrganizations." +
            "pkOrganization,privateServicePoints.name," +
            "privateServicePoints.pkServicePoint";
    }

    const url= SERVER_URL + "api/commonuser/me?" + convertJson2Url(queryParams);

    return fetch(url).then(function(response){
        return response.json();
    });
};