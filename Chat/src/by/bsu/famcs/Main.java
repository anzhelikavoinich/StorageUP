package by.bsu.famcs;

import java.io.*;

// перед добавлением и удалением загрузить из файла переписку,
// сохранение автоматически при выходе
// перед просмотром в хронологическом порядке не нужно делать загрузку из файла,
// она делается автоматически
public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        MenuManager menuManager = new MenuManager();
        int count = menuManager.getCount();
        boolean flag = false;
        while(count != 0) {
            if (count == 1) {
                menuManager.loadData();
            }
            if (count == 2) {
                menuManager.saveChanges();
            }
            if (count == 3) {
                menuManager.addMessage();
                flag = true;
            }
            if (count == 4) {
                menuManager.viewHistory();
            }
            if (count == 5) {
                menuManager.delMessage();
                flag = true;
            }
            if(count == 6){
                menuManager.searchByAuthor();
            }
            if(count == 7){
                menuManager.searchByLexeme();
            }
            if(count == 8){
                menuManager.searchByRegex();
            }
            if(count == 9){
                menuManager.viewHistoryPeriod();
            }
            count = menuManager.getCount();
        }
        if(flag){
            menuManager.saveChanges();
        }
    }
}