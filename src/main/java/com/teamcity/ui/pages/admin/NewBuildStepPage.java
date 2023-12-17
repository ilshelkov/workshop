package com.teamcity.ui.pages.admin;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.teamcity.api.generators.RandomData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.teamcity.ui.Selectors.byDataTest;

public class NewBuildStepPage extends EditBuildPage {

    private static final String NEW_BUILD_STEP_URL = "/admin/editRunType.html?id=buildType:%s&runnerId=__NEW_RUNNER__";
    private static final String COMMAND_LINE_RUNNER_TYPE = "Command Line";
    private static final String CUSTOM_SCRIPT_INPUT_SELECTOR = "[id='script.content']";
    private final SelenideElement runnerItemFilterInput = $(byDataTest("runner-item-filter"));
    private final SelenideElement buildStepNameInput = $("#buildStepName");
    private final ElementsCollection runnerItems = $$(byDataTest("runner-item"));

    public NewBuildStepPage() {
        runnerItemFilterInput.shouldBe(visible, baseWaiting);
    }

    public static NewBuildStepPage open(String buildTypeId) {
        Selenide.open(NEW_BUILD_STEP_URL.formatted(buildTypeId));
        return page(NewBuildStepPage.class);
    }

    public EditBuildPage createCommandLineBuildStep(String customScript) {
        runnerItems.findBy(text(COMMAND_LINE_RUNNER_TYPE)).click();
        buildStepNameInput.shouldBe(visible, baseWaiting).val(RandomData.getString());
        executeJavaScript("document.querySelector('%s').innerText = '%s'".formatted(CUSTOM_SCRIPT_INPUT_SELECTOR, customScript));
        submitButton.click();
        return this;
    }

}
