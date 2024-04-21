package landforlife.tn.interfaces;

import java.util.List;

public interface Dinterface<D> {
    //CRUD
    //1
    void add(D Demande);
    //2
    void update(D Demande);
    //3
    void delete(D demande);
    //4
    List<D> getAll();
    //5
    D getOne(int id);
}
