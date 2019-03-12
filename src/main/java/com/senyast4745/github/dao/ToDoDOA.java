package com.senyast4745.github.dao;


import com.senyast4745.github.model.ToDo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

//Create only one exemplar (like singleton) так что нужно обратиться к этому компоненте  использовать Autowired
@Component
public class ToDoDOA {
    //private static final String template = "oDo #%d";
    private static ConcurrentLinkedDeque<ToDo> allToDos = new ConcurrentLinkedDeque<>();
    private final AtomicLong counter = new AtomicLong();

    public ToDo create(String description) {
        long id = counter.incrementAndGet();
        ToDo toDo = new ToDo(id, description);
        allToDos.add(toDo);
        //String desc = String.format(template, id);
        return toDo;
    }

    public ToDo read(long id) {
        for (ToDo toDo : allToDos) {
            if (toDo.getId() == id) {
                return toDo;
            }
        }
        return null;
    }

    public boolean delete(long id) {
        ToDo toDoDel = null;
        for (ToDo toDo : allToDos) {
            if (toDo.getId() == id) {
                toDoDel = toDo;
            }
        }
        if (toDoDel != null) {
            allToDos.remove(toDoDel);
            return true;
        } else {
            return false;
        }
    }

    public ToDo update(long id, String description) {
        if (delete(id)) {
            ToDo toDo = new ToDo(id, description);
            allToDos.add(toDo);
            return toDo;
        } else {
            return null;
        }
    }

    public ConcurrentLinkedDeque<ToDo> showAll() {
        return allToDos;
    }

    public boolean clearList() {
        allToDos.clear();
        return allToDos.size() == 0;
    }
}
