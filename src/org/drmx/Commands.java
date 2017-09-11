package org.drmx;

public enum Commands {

    CINEMA_TODAY("/Обнинск кино", "Расписание сеансов кинотеатра 'Синема де люкс' на сегодня"),
    OWNER("/хозяин", "Мой хозяин"),
    HELP("/help", "Справка");
    String id;
    String description;

    Commands(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
