package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.swipeUp;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomExecutor;

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
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.ScreenElements.ClaimsScreen;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.CommentSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.CreateClaimSteps;
import ru.iteco.fmhandroid.ui.steps.EditClaimSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsTest {
    AuthSteps authSteps = new AuthSteps();
    ClaimsSteps claimsSteps = new ClaimsSteps();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    CreateClaimSteps createClaimSteps = new CreateClaimSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    Resources resources = new Resources();
    CommonSteps commonSteps = new CommonSteps();
    CommentSteps commentSteps = new CommentSteps();
    EditClaimSteps editClaimsSteps = new EditClaimSteps();


    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            claimsSteps.isClaimsScreen();
        } catch (NoMatchingViewException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
            SystemClock.sleep(5000);
        } finally {
            mainScreenSteps.clickAllClaims();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана Claims")
    @Description("Корректность отображения всех элементов экрана")
    public void shouldCheckClaimsScreenElements() {
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Проверка элементов претензии")
    @Description("Корректность отображения всех элементов претензии")
    public void shouldCheckClaimElements() {
        int index = 0;
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(5000);
        claimsSteps.checkClaimElements();
    }

    @Test
    @DisplayName("Открыть и закрыть претензию")
    @Description("При нажатии на претензию открывается ее содержание")
    public void shouldOpenElementAndReturn() {
        int index = 0;
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(5000);
        claimsSteps.checkClaimElements();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Открыть окно фильтра претензий")
    @Description("При нажатии на кнопку фильтра открывается окно с фильтром претензий")
    public void shouldCheckClaimsFilterWindow() {
        claimsSteps.openFilterWindow();
        claimsSteps.isFilterWindow();
    }

    @Test
    @DisplayName("Убрать отметку во всех чек-боксах фильтра")
    @Description("При фильтрации претензий без статуса претензий не отображается, надпсиь There is nothing here yet")
    public void shouldCheckNoClaimsAreDisplayed() {
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        SystemClock.sleep(2000);
        commonSteps.checkClaimButterflyImage();
        commonSteps.checkNothingToShowScreen();
    }

    @Test
    @DisplayName("Отфильтровать претензии со статусом Open")
    @Description("При фильтрации претензий по статусу Open отображаются только претензии со статусом Open")
    public void shouldShowOpenClaims() {
        int index = random(0, 1, 2);
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        SystemClock.sleep(2000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(5000);
        claimsSteps.checkClaimStatus("Open");
    }

    @Test
    @DisplayName("Отфильтровать претензии со статусом In Progress")
    @Description("При фильтрации претензий по статусу In progress отображаются только претензии со статусом In progress")
    public void shouldShowInProgressClaims() {
        int index = random(0, 1, 2);
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        commonSteps.clickOkBtn();
        SystemClock.sleep(5000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(5000);
        claimsSteps.checkClaimStatus("In progress");
    }

    @Test
    @DisplayName("Отфильтровать претензии со статусом Executed")
    @Description("При фильтрации претензий по статусу Executed отображаются только претензии со статусом Executed")
    public void shouldShowExecutedClaims() {
        int index = random(0, 1, 2);
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        claimsSteps.clickExecuted();
        commonSteps.clickOkBtn();
        SystemClock.sleep(5000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(5000);
        claimsSteps.checkClaimStatus("Executed");
    }

    @Test
    @DisplayName("Отфильтровать претензии со статусом Cancelled")
    @Description("При фильтрации претензий по статусу Cancelled отображаются только претензии со статусом Cancelled")
    public void shouldShowCancelledClaims() {
        int index = random(0, 1, 2);
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        claimsSteps.clickCancelled();
        commonSteps.clickOkBtn();
        SystemClock.sleep(8000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(5000);
        claimsSteps.checkClaimStatus("Canceled");
    }

    @Test
    @DisplayName("Создать претензию с валидными значениями на кириллице")
    @Description("При заполнении текстовых полей данными на кириллице и вводе валидных значений в поля дата и время создается претензия на кириллице")
    public void shouldCreateNewClaimCyr() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillInTime(resources.claimPublicationTime);
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Создать претензию с валидными значениями на латинице")
    @Description("При заполнении текстовых полей данными на латинице и вводе валидных значений в поля дата и время создается претензия на латинице")
    public void shouldCreateNewClaimLatin() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillInTime(resources.claimPublicationTime);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Ручной ввод невалидного часа")
    @Description("При ручном вводе в поле время невалидного часа всплывает предупреждение о невалидном времени")
    public void shouldShowWrongHourWarning() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("25", "25");
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("Ручной ввод невалидных минут")
    @Description("При ручном вводе в поле время невалидных минут всплывает предупреждение о невалидном времени")
    public void shouldShowWrongMinuteWarning() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("15", "75");
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("Создать претензию с ручным вводом валидного времени")
    @Description("При ручном вводе в поле время валидного времени претензия создается")
    public void shouldCreateClaimWithManualTimeInput() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("01", "00");
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        assertEquals("01:00", claimsSteps.getClaimTime());
    }

    @Test
    @DisplayName("Создать претензию с пустой датой и временем")
    @Description("Если поля время и дата остаются пустыми, претензия не создается, предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateClaimWithEmptyTimeAndDate() {
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        commonSteps.checkEmptyMessage(R.string.empty_fields, true);
    }

    @Test
    @DisplayName("Создать пустую претензию")
    @Description("Претензия с пустыми полями не заполняется, всплывает предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateEmptyClaim() {
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        commonSteps.checkEmptyMessage(R.string.empty_fields, true);
    }

    @Test
    @DisplayName("Создать претензию с названием более 50 символов")
    @Description("При вводе в поле Title названия из более чем 50 символов сохраняются только 50 символов")
    public void shouldCheckTitleLength() {
        int index = 0;
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.fillInTitle(resources.claimTitle51);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        claimsSteps.getClaimTitle();
        assertEquals("В этом названии теперь больше пятидесяти символов!", claimsSteps.getClaimTitle());
    }

    @Test
    @DisplayName("Создать претензию с пробелами в названии и описании")
    @Description("При заполнении полей title и description пробелами претензия не создается, предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateClaimsWithSpaces() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleSpace);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillInTime(resources.claimPublicationTime);
        createClaimSteps.fillItDescription(resources.claimDescriptionSpace);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        commonSteps.checkEmptyMessage(R.string.empty_fields, true);
    }


    @Test
    @DisplayName("Нажать отмену, вернуться к созданию и отменить создание претензии")
    @Description("При нажатии отмены без подтверждения продолжается создание претензии, при подтверждении отмены создание претензии прекращается")
    public void shouldCancelClaimCreation() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickCancel();
        SystemClock.sleep(3000);
        commonSteps.checkEmptyMessage(R.string.cancellation, true);
        commonSteps.clickCancelInDialog();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkEmptyMessage(R.string.cancellation, true);
        commonSteps.clickOkBtn();
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Изменить статус претензии с Open на In progress и обратно")
    @Description("При нажатии на кнопку Take to work статус претензии меняется на In progress, при нажатии на throw off претензия меняет статус на Open")
    public void shouldChangeClaimStatusToInProgressAndBack() {
        int index = 0;
        String comment = "Обеда не будет";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        SystemClock.sleep(2000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("Open");
        SystemClock.sleep(3000);
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickTakeToWork();
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("In progress");
        SystemClock.sleep(2000);
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickThrowOff();
        claimsSteps.addCommentWhenStatusChange(comment);
        commonSteps.clickOkBtn();
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("Open");
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Изменить статус претензии с Open на Executed")
    @Description("При нажатии на кнопку Take to work статус претензии меняется на In progress, при нажатии на to execute статус претензии меняется на Executed")
    public void shouldChangeClaimStatusToExecuted() {
        int index = 0;
        String comment = "Принято";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        SystemClock.sleep(2000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("Open");
        SystemClock.sleep(3000);
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickTakeToWork();
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("In progress");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickToExecute();
        claimsSteps.addCommentWhenStatusChange(comment);
        commonSteps.clickOkBtn();
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("Executed");
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Изменить статус претензии с Open на Cancelled")
    @Description("При нажатии на кнопку Take to work статус претензии меняется на In progress, при нажатии на cancel статус претензии меняется на Canceled")
    public void shouldChangeClaimStatusToCancelled() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        SystemClock.sleep(2000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("Open");
        SystemClock.sleep(3000);
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickCancelClaim();
        SystemClock.sleep(3000);
        claimsSteps.checkClaimStatus("Canceled");
    }

    @Test
    @DisplayName("Добавить комментарий на кириллице к претензии")
    @Description("При нажатии на кнопку добавить комментарий открывается окно комментраия, поле заполняется текстом на кириллице, комментарий сохравняется")
    public void shouldAddCyrCommentToClaim() {
        int index = 0;
        String comment = resources.commentCyr;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        SystemClock.sleep(5000);
        claimsScreen.statusIcon.perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Добавить комментарий на латинице к претензии")
    @Description("При нажатии на кнопку добавить комментарий открывается окно комментария, поле заполняется текстом на латинице, комментарий сохраняется")
    public void shouldAddLatinCommentToClaim() {
        int index = 0;
        String comment = resources.commentLatin;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        SystemClock.sleep(5000);
        claimsScreen.statusIcon.perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
        claimsSteps.checkCommentToClaim(comment);
    }


    @Test
    @DisplayName("Редактировать комментарий")
    @Description("При нажатии на кнопку редактирования комменатрия и изменения текста комментария, отображается измененный комменатрий")
    public void shouldEditComment() {
        int index = 0;
        String initialComment = resources.commentCyr;
        String editedComment = resources.editedComment;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(initialComment);
        commonSteps.clickSave();
        SystemClock.sleep(5000);
        claimsSteps.clickCommentEditBtn(index);
        commentSteps.addComment(editedComment);
        commonSteps.clickSave();
        claimsSteps.checkCommentToClaim(editedComment);
    }

    @Test
    @DisplayName("Отменить редактирование комментария")
    @Description("При нажатии на отмену редактироваия комментария комментарий не сохраняется")
    public void shouldCancelCommentEditing() {
        int position = 0;
        int index = 0;
        int commentIndex = 0;
        String initialComment = resources.commentCyr;
        String editedComment = resources.editedComment;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(initialComment);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        String initialCommentContent = claimsSteps.getClaimComment(position);
        SystemClock.sleep(3000);
        claimsSteps.clickCommentEditBtn(commentIndex);
        commentSteps.addComment(editedComment);
        commonSteps.clickCancel();
        SystemClock.sleep(3000);
        String commentAfterCancelledEditing = claimsSteps.getClaimComment(position);
        assertEquals(initialCommentContent, commentAfterCancelledEditing);
    }

    @Test
    @DisplayName("Отменить создание комментария")
    @Description("При нажатии на отмену комментарий не добавляется")
    public void shouldCancelCommentAddition() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        SystemClock.sleep(3000);
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(3000);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commonSteps.clickCancel();
        SystemClock.sleep(3000);
        claimsSteps.statusIconIsDisplayed();
    }


    @Test
    @DisplayName("Редактировать претензию")
    @Description("При нажатии на редактирвание и редактировании данных в полях претензии претензия отображается с новми данными")
    public void shouldEditClaim() {
        int index = 0;
        String editedTitle = resources.claimEditedTitle;
        String editedDescription = resources.claimEditedDescription;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(3000);
        claimsSteps.clickEditClaim();
        editClaimsSteps.changeClaimTitle(editedTitle);
        editClaimsSteps.changeDescription(editedDescription);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        assertEquals(editedTitle, claimsSteps.getClaimTitle());
        assertEquals(editedDescription, claimsSteps.getClaimDescription());
    }

    @Test
    @DisplayName("Отменить редактирование претензию")
    @Description("При отмене редактирования без подтверждения продолжается редактирование, при подтверждении отмены претензия не редактируется")
    public void shouldCancelClaimEditing() {
        int index = 0;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        SystemClock.sleep(3000);
        claimsSteps.openClaimIndex(index);
        SystemClock.sleep(3000);
        claimsSteps.clickEditClaim();
        commonSteps.clickCancel();
        SystemClock.sleep(3000);
        commonSteps.checkEmptyMessage(R.string.cancellation, true);
        commonSteps.clickCancelInDialog();
        editClaimsSteps.isEditClaimScreen();
        commonSteps.clickCancel();
        SystemClock.sleep(3000);
        commonSteps.checkEmptyMessage(R.string.cancellation, true);
        commonSteps.clickOkBtn();
        claimsSteps.statusIconIsDisplayed();
    }
}
