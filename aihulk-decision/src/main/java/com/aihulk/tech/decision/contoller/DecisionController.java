package com.aihulk.tech.decision.contoller;

import com.aihulk.tech.decision.component.mvc.annotation.Action;
import com.aihulk.tech.decision.component.mvc.annotation.RequestMethod;
import com.aihulk.tech.decision.component.mvc.annotation.Router;
import com.aihulk.tech.decision.component.mvc.data.RequestParam;
import com.aihulk.tech.decision.component.mvc.data.response.JsonResponse;
import com.aihulk.tech.decision.component.mvc.data.response.Response;

import java.util.Date;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:DecisionController
 * @Description: DecisionController
 * @date 2019/8/1
 */
@Router
public class DecisionController {

    @Action(value = "/decision", method = {RequestMethod.GET})
    public Response decision(RequestParam param) {
        Response response = new JsonResponse();
        response.put("date", new Date());
        response.put("name", "Michael Yan");
        System.out.println("Query params:" + param.getString("a"));
        return response;
    }

}
