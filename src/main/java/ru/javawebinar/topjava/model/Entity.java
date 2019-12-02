package ru.javawebinar.topjava.model;

abstract class Entity {
    private Long id;

//    private long generateId() {
//        ModelView annotation = this.getClass().getAnnotation(ModelView.class);
//        return (annotation != null && annotation.useUniqueId()) ? new UniqueId().generate() : null;
//        return UserMealDao.getInstance().getUniqueId();
//    }


    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
