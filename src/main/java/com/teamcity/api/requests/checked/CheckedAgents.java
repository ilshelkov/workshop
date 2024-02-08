package com.teamcity.api.requests.checked;

import com.teamcity.api.models.Agents;
import com.teamcity.api.models.BaseModel;
import com.teamcity.api.requests.CrudInterface;
import com.teamcity.api.requests.Request;
import com.teamcity.api.requests.unchecked.UncheckedAgents;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedAgents extends Request implements CrudInterface {

    public CheckedAgents(RequestSpecification spec) {
        super(spec, null);
    }

    @Override
    public Object create(BaseModel model) {
        return null;
    }

    @Override
    public Agents read(String id) {
        return new UncheckedAgents(spec)
                .read(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Agents.class);
    }

    @Override
    public BaseModel update(String id, BaseModel model) {
        var operation = model.getClass().getSimpleName();
        operation = "/" + Character.toLowerCase(operation.charAt(0)) + operation.substring(1);
        return new UncheckedAgents(spec)
                .update(id + operation, model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(model.getClass());
    }

    @Override
    public Object delete(String id) {
        return null;
    }

}
