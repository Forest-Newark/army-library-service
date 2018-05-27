package com.forestnewark.Library.Manager.Repository;

import com.forestnewark.Library.Manager.bean.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Forest on 11/25/17.
 */
public interface CompositionRepository extends JpaRepository<Composition,Integer> {


    @Query("SELECT DISTINCT c.catagory FROM Composition c")
    List<String> findDistinctCatagory();

}
