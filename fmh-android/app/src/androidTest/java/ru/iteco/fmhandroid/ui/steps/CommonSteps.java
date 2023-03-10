package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.screenElements.CommonElements;
import ru.iteco.fmhandroid.ui.screenElements.TimeScreen;

public class CommonSteps {
    CommonElements commonElements = new CommonElements();
    TimeScreen timeScreen = new TimeScreen();

    public void clickSave() {
        Allure.step("Кликнуть SAVE");
        commonElements.saveBtn.perform(click());
    }

    public void clickCancel() {
        Allure.step("Кликнуть CANCEL");
        commonElements.cancelBtn.perform(click());
    }

    public void clickOkBtn() {
        Allure.step("Кликнуть ок");
        commonElements.okBtn.perform(click());
    }


    public void checkEmptyMessage(int id, boolean visible) {
        Allure.step("Проверка текста сообщения");
        if (visible) {
            commonElements.emptyMessage(id).check(matches(isDisplayed()));
        } else {
            commonElements.emptyMessage(id).check(matches(not(isDisplayed())));
        }
    }

    public void checkNewsButterflyImage() {
        Allure.step("Проверка картинки с бабочкой вкладки News");
        commonElements.butterflyImageNews.check(matches(isDisplayed()));
    }

    public void checkControlPanelButterflyImage() {
        Allure.step("Проверка картинки с бабочкой вкладки Control panel");
        commonElements.butterflyImageControlPanel.check(matches(isDisplayed()));
    }

    public void checkClaimButterflyImage() {
        Allure.step("Проверка картинки с бабочкой экрана Claims");
        commonElements.butterflyImageClaims.check(matches(isDisplayed()));
    }

    public void checkNothingToShowScreen() {
        Allure.step("Проверка элементов экрана Nothing to show");
        commonElements.nothingToShowWarning.check(matches(isDisplayed()));
        commonElements.refreshBtn.check(matches(isDisplayed()));
    }

    public void manualTimeInput(String hour, String minute) {
        Allure.step("Ввести время вручную");
        timeScreen.manualTimeInputBtn.perform(click());
        timeScreen.inputHour.perform(replaceText(hour));
        timeScreen.inputMinute.perform(replaceText(minute));
        clickOkBtn();
    }

    public void checkWrongTimeError() {
        Allure.step("Проверка предупреждения о невалидном значении времени");
        elementWaiting(withText("Enter a valid time"), 10000);
        commonElements.wrongTimeError.check(matches(isDisplayed()));
    }

    public void clickCancelInDialog() {
        Allure.step("Кликнуть Cancel в диалоговом окне");
        commonElements.cancelInDialog.perform(click());
    }

}
