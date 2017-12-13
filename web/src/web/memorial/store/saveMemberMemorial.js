import {fetch, save, getWhatwgFetch, customParam} from "../../../common/fetch/fetch";
import {SERVER_URL} from "../../../common/constant/serverConfig";


module.exports = {
    //更新密码
    save(params){
        return save(SERVER_URL + "api/membermemorial/savemembermemorial",{
            body: customParam(params)
        });
    }
};
