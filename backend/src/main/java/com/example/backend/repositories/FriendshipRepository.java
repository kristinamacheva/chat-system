package com.example.backend.repositories;

import com.example.backend.entities.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM Friendship f " +
            "WHERE ((f.user1.id = :userId1 AND f.user2.id = :userId2) " +
            "   OR (f.user1.id = :userId2 AND f.user2.id = :userId1)) " +
            "   AND f.isActive = 1")
    boolean existsActiveFriendship(@Param("userId1") int userId1, @Param("userId2") int userId2);
}
