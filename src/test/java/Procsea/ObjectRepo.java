package Procsea;

import org.openqa.selenium.By;

public class ObjectRepo {

    public static final By seeAllList=By.cssSelector("[class='ul-costs']");
    public static final By filterByUser=By.id("paidBy");
    public static final By addNewExpense=By.id("title");
    public static final By paidAmount=By.id("amount");
    public static final By buttonExpense=By.cssSelector("button[class='button-expense']");
    public static final By totalExpense=By.cssSelector("div[class='total'] div ~ div");



}
