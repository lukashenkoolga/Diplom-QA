package ru.iteco.fmhandroid.ui.test;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.OurMissionSteps;

@RunWith(AllureAndroidJUnit4.class)
public class OurMissionTest {
    AuthSteps authSteps = new AuthSteps();
    OurMissionSteps ourMissionSteps = new OurMissionSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    NewsSteps newsSteps = new NewsSteps();

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            newsSteps.isNewsScreen();
        } catch (NoMatchingViewException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
            SystemClock.sleep(5000);
        } finally {
            mainScreenSteps.clickOurMissionBtn();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Проверка элементов экрана с тематическими цитатами")
    @Description("Корректность отображения всех элементов экрана с тематическими цитатами")
    public void shouldCheckMissionScreenElements() {
        ourMissionSteps.isOurMissionScreen();
    }

    @Test
    @DisplayName("Свернуть/развернуть цитату")
    @Description("При нажати на цитату разворачивается ее содержимое")
    public void shouldShowOrHideQuoteDescription() {
        ourMissionSteps.descriptionNotDisplayed("Все сотрудники хосписа - это адвокаты пациента, его прав и потребностей. Поиск путей решения различных задач - это и есть хосписный индивидуальный подход к паллиативной помощи.");
        SystemClock.sleep(3000);
        ourMissionSteps.showOrHideQuote(2);
        ourMissionSteps.descriptionIsDisplayed("Все сотрудники хосписа - это адвокаты пациента, его прав и потребностей. Поиск путей решения различных задач - это и есть хосписный индивидуальный подход к паллиативной помощи.");

    }
}
