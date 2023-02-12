package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.screenElements.CreateNewsScreen;

public class CreateNewsSteps {
    CreateNewsScreen createNewsScreen = new CreateNewsScreen();

    public void isCreatingNewsScreen() {
        Allure.step("Проверка элементов экрана Creating News");
        createNewsScreen.creatingNewsScreenName.check(matches(isDisplayed()));
        createNewsScreen.categoryName.check(matches(isDisplayed()));
        createNewsScreen.categoryField.check(matches(isDisplayed()));
        createNewsScreen.titleField.check(matches(isDisplayed()));
        createNewsScreen.titleName.check(matches(isDisplayed()));
        createNewsScreen.publicationDateField.check(matches(isDisplayed()));
        createNewsScreen.publicationDateName.check(matches(isDisplayed()));
        createNewsScreen.timeField.check(matches(isDisplayed()));
        createNewsScreen.titleName.check(matches(isDisplayed()));
        createNewsScreen.descriptionField.check(matches(isDisplayed()));
        createNewsScreen.descriptionName.check(matches(isDisplayed()));
    }

    public void fillInNewsCategory(String text) {
        Allure.step("Заполнить поле категория");
        createNewsScreen.categoryField.perform(replaceText(text));
    }

    public void fillInNewsTitle(String text) {
        Allure.step("Заполнить поле заголовок");
        createNewsScreen.titleField.perform(replaceText(text));
    }

    public void fillInPublicationDate(String text) {
        Allure.step("Заполнить поле дата публикации");
        createNewsScreen.publicationDateField.perform(replaceText(text));
    }

    public void fillInTime(String text) {
        Allure.step("Заполнить поле время");
        createNewsScreen.timeField.perform(replaceText(text));
    }

    public void fillInNewsDescription(String text) {
        Allure.step("Заполнить поле описание");
        createNewsScreen.descriptionField.perform(replaceText(text));
    }

    public void createNews(String category, String title, String publicationDate, String publicationTime, String description) {
        Allure.step("Создать новость");
        fillInNewsCategory(category);
        fillInNewsTitle(title);
        fillInPublicationDate(publicationDate);
        fillInTime(publicationTime);
        fillInNewsDescription(description);
    }

    public void clickTimeField() {
        Allure.step("Кликнуть поле время");
        createNewsScreen.timeField.perform(click());
    }
}
