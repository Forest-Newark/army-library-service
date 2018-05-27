package com.forestnewark.Library.Manager.Repository;

import com.forestnewark.Library.Manager.bean.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by forestnewark on 2/8/18.
 */
public interface LibraryUserRepository extends JpaRepository<LibraryUser,Integer> {

    LibraryUser findByUserName(@Param("userName")String userName);
}
