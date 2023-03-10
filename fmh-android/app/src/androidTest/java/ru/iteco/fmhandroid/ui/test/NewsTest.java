package ru.iteco.fmhandroid.ui.test;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreateNewsSteps;
import ru.iteco.fmhandroid.ui.steps.EditNewsSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {
    AuthSteps authSteps = new AuthSteps();
    NewsSteps newsSteps = new NewsSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    FilterNewsSteps filterScreen = new FilterNewsSteps();
    Resources resources = new Resources();
    CreateNewsSteps createNewsSteps = new CreateNewsSteps();
    CommonSteps commonSteps = new CommonSteps();
    EditNewsSteps editNewsSteps = new EditNewsSteps();


    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (NoMatchingViewException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
            SystemClock.sleep(5000);
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
            mainScreenSteps.clickAllNews();
        }
    }

    @Test
    @DisplayName("???????????????? ?????????????????? ???????????? News")
    @Description("???????????????????????? ?????????????????????? ???????? ?????????????????? ???????????? News")
    public void shouldCheckNewsScreenElements() {
        newsSteps.isNewsScreen();
    }

    @Test
    @DisplayName("???????????????????? ?????????? ??????????????")
    @Description("?????? ?????????????? ???? ?????????????? ?????????????????????????????? ???? ????????????????????")
    public void shouldShowNewsContent() {
        int position = random(0, 1, 2, 3);
        newsSteps.openNews(position);
    }

    @Test
    @DisplayName("???????????? ???? ????????")
    @Description("???????????????????????? ???????????? ???? ??????????????, ?????????????? ???????? ?????????????? ?? ?????????????????? ???????????????????? ??????????????")
    public void shouldFilterByDate() {
        String newsDate = resources.newsPublicationDate;
        newsSteps.openFilter();
        filterScreen.isFilterScreen();
        filterScreen.enterStartDate(newsDate);
        filterScreen.enterEndDate(newsDate);
        filterScreen.clickFilter();
        newsSteps.checkFirstNewsPublicationDate();
    }

    @Test
    @DisplayName("?????? ????????????????, ?????????????????????????????? ??????????????????")
    @Description("?????? ?????????????????? ????????????????, ?????????????????????????????? ?????????????????? ???????????? (???????? ????????????????), ???? ???????????? ?????????? ?????????????? There is nothing here yet")
    public void shouldShowNothingToShowScreen() {
        String newsDate = resources.dateForNonExistentNews;
        newsSteps.openFilter();
        filterScreen.isFilterScreen();
        filterScreen.enterStartDate(newsDate);
        filterScreen.enterEndDate(newsDate);
        filterScreen.clickFilter();
        commonSteps.checkNewsButterflyImage();
        commonSteps.checkNothingToShowScreen();
    }

    @Test
    @DisplayName("???????????? ????????????????????")
    @Description("?????????? ???? ???????????? ?????????????? ?????? ???????????????????? ????????????????")
    public void shouldCancelFilter() {
        String newsDate = resources.newsPublicationDate;
        newsSteps.openFilter();
        filterScreen.isFilterScreen();
        filterScreen.enterStartDate(newsDate);
        commonSteps.clickCancel();
        newsSteps.isNewsScreen();
    }

    @Test
    @DisplayName("?????????????? ?? ???????????? ????????????????????")
    @Description("?????? ?????????????? ???? ???????????? ?? ???????? ???????????????? ?? ???????????????????? ???????????????????????? ?????????????????? ???? ?????????????? Control panel")
    public void shouldTransferToControlPanel() {
        newsSteps.clickEditBtn();
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("???????????????? ??????-???????????? ???????????????????????? ??????????????")
    @Description("?????? ?????????????? ???? ??????-?????????? ?????? ???????????????????? ??????????????????????")
    public void shouldOpenFilter() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnActiveCheckBox();
        filterScreen.checkActiveCheckBoxStatus(false);
        filterScreen.clickOnNotActiveCheckBox();
        filterScreen.checkNotActiveCheckBoxStatus(false);
    }

    @Test
    @DisplayName("???????????? ???????????????? ?????????? ???????????? ???????????????????? ???? ??????????????")
    @Description("?????? ?????????????? ???????????????? ???? ?????????????? Active/Not Active ?? ???????????? ???????????????? ???????????????????????? ???????????? ?????????????? ?? ???????? ????????????????")
    public void shouldCheckFilteredNewsStatus() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnNotActiveCheckBox();
        filterScreen.clickFilter();
        newsSteps.newsListLoaded();
        controlPanelSteps.checkActiveNewsStatus();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnActiveCheckBox();
        filterScreen.clickFilter();
        newsSteps.newsListLoaded();
        controlPanelSteps.checkNotActiveNewsStatus();
    }

    @Test
    @DisplayName("?????? ????????????????, ?????????????????????????????? ??????????????????, ?? ???????????? ????????????????????")
    @Description("?????? ?????????????????? ????????????????, ?????????????????????????????? ?????????????????? ????????????, ???????????????????????? ?????????? ?????????? ?? ???????????????? There is nothing here yet")
    public void shouldShowNothingToShowScreenInControlPanel() {
        String newsDate = resources.dateForNonExistentNews;
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.enterStartDate(newsDate);
        filterScreen.enterEndDate(newsDate);
        filterScreen.clickFilter();
        commonSteps.checkControlPanelButterflyImage();
        commonSteps.checkNothingToShowScreen();
    }

    @Test
    @DisplayName("???????????? ?????????????? ???????????????? ?????????? ???????????? ????????????????????")
    @Description("?????????? ???? ?????????????? ?? ?????????????? ???????????? ????????????")
    public void shouldCancelFiltering() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        commonSteps.clickCancel();
        controlPanelSteps.isControlPanelScreen();
    }


    @Test
    @DisplayName("?????????????? ?????????????? ?? ???????????? ???????????? ?????????????????????? ????????")
    @Description("?????? ???????????? ?????????? ?????????????????????? ???????? ?????? ???????????????? ?????????????? ?????????????????? ???????????????????????????? ?? ???????????????????? ???????????????? ??????????????")
    public void shouldShowInvalidHourWarning() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.fillInNewsCategory(randomCategory());
        createNewsSteps.fillInNewsTitle(resources.newsTitleCyr);
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.clickTimeField();
        commonSteps.manualTimeInput("25", "25");
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("?????????????? ?????????????? ?? ???????????? ???????????? ???????????????????? ??????????")
    @Description("?????? ???????????? ?????????? ?????????????????????? ???????????????? ?????????? ?????? ???????????????? ?????????????? ?????????????????? ???????????????????????????? ?? ???????????????????? ???????????????? ??????????????")
    public void shouldShowInvalidMinuteWarning() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.fillInNewsCategory(randomCategory());
        createNewsSteps.fillInNewsTitle(resources.newsTitleCyr);
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.clickTimeField();
        commonSteps.manualTimeInput("15", "75");
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("???????????????? ???????????????? ??????????????")
    @Description("?????? ?????????????? ???? ???????????? ???????????? ?? ?????????????????????????? ???????????? ?????????????? ???? ??????????????????")
    public void shouldCancelNewsCreation() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.fillInTime(resources.newsPublicationTime);
        commonSteps.clickCancel();
        commonSteps.clickOkBtn();
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("???????????????? ???????????????? ?????????????? ?? ?????????????????? ?? ????????????????")
    @Description("?????? ?????????????? ???? ???????????? ???????????? ?????? ?????????????????????????? ???????????????????????? ???????????????????? ???????????????? ??????????????")
    public void shouldCancelNewsCreationAndReturn() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.fillInTime(resources.newsPublicationTime);
        commonSteps.clickCancel();
        commonSteps.clickCancelInDialog();
        createNewsSteps.isCreatingNewsScreen();
    }

    @Test
    @DisplayName("???????????????? ???????????? ?????????????????? ??????????????")
    @Description("?????? ?????????????? ???? ???????????????????????????? ?????????? ???????????????? ???????????? ?????????????? ?? Active ???? Not Active ?? ??????????????. ?????????????? ???????????????????????? ?? ?????????? ????????????????")
    public void shouldEditNews() {
        int position = 0;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        newsSteps.newsListLoaded();
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.editStatus();
        newsSteps.newsListLoaded();// to "not active"
        controlPanelSteps.checkNotActiveNewsStatus();
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.editStatus(); // to "active"
        controlPanelSteps.checkActiveNewsStatus();
    }

    @Test
    @DisplayName("???????????????????????????? ??????????????")
    @Description("?????? ?????????????? ???? ?????????????????????????? ?????????????? ?? ?????????????????? ???????????? ?????????????? ???????????????????????? ?? ???????????? ??????????????")
    public void shouldEditNewsTitleAndDescription() {
        int position = 0;
        String newTitle = "?????????? ????????????????";
        String newDescription = "?????????? ????????????????";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        newsSteps.newsListLoaded();
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editTitle(newTitle);
        editNewsSteps.editDescription(newDescription);
        commonSteps.clickSave();
        controlPanelSteps.clickOnRandomlySelectedNewsItem(position);
        assertEquals(newTitle, controlPanelSteps.getEditedNewsTitle(position));
        assertEquals(newDescription, controlPanelSteps.getEditedNewsDescription(position));
    }


    @Test
    @DisplayName("???????????? ???????????????????????????? ?????????????? ?? ?????????????? ?? ????????????????????????????")
    @Description("???????? ???????????? ???????????????????????????? ???? ????????????????????????????, ???????????????????????????? ???????????? ?????????? ???????? ????????????????????")
    public void shouldCancelNewsEditingAndReturnToEditing() {
        int position = 0;
        String newTitle = "?????????? ????????????????";
        String newDescription = "?????????? ????????????????";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        newsSteps.newsListLoaded();
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editTitle(newTitle);
        editNewsSteps.editDescription(newDescription);
        commonSteps.clickCancel();
        commonSteps.clickCancelInDialog();
        editNewsSteps.isEditNewsScreen();
    }

    @Test
    @DisplayName("???????????????? ??????????????")
    @Description("?????? ?????????????? ?? ?????????????????????????? ???????????????? ?????????????? ??????????????????")
    public void shouldDeleteNews() {
        String title = "?????????????????? ??????????????";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), title, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        newsSteps.newsListLoaded();
        controlPanelSteps.deleteNews("?????????????????? ??????????????");
        controlPanelSteps.confirmDeleting();
        newsSteps.newsListLoaded();
        controlPanelSteps.isControlPanelScreen();
    }
}
