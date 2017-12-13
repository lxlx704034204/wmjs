import {save,customParam} from "../../../common/fetch/fetch";
import {SERVER_URL} from "../../../common/constant/serverConfig";


module.exports = function (queryParams) {
    const url= SERVER_URL + "api/generaluser/save";

    return save(url,{
        body: customParam(queryParams)
    });
};
