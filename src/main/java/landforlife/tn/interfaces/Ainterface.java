package landforlife.tn.interfaces;

import java.util.List;

public interface Ainterface<A> {
    //CRUD
    //1
    void add(A Animal);
    //2
    void update(A Animal);
    //3
    void delete(A Animal);

    List<A> getAll();

    A getOne(int id);
}
