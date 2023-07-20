package server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> getUsersByRole(String role);

    User getUserByUsername(String username);

    User getUserByUserId(int userId);

    @Modifying
    @Query("UPDATE User u SET u.accountLocked = :accountLocked WHERE u.username = :username")
    int updateUserAccountStatus(@Param("username") String username, @Param("accountLocked") boolean accountLocked);

}
