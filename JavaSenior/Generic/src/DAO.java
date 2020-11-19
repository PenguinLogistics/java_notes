/*
* DAO: data(base) access object
*
* */

import java.util.List;

public class DAO<T> {//表的共性操作的DAO
    //添加一条记录
    public void add(T t){

    }
    //删除一条记录
    public boolean remove(int idx){
        return false;
    }
    //修改一条记录
    public void update(int idx, T t){

    }
    //查询一条记录
    public T getIdx(int idx){
        return null;
    }
    //查询多条记录
    public List<T> getForList(int index){
        return null;
    }

    public <E> E getValue(){
        return null;
    }

    public void show(){

    }
}
