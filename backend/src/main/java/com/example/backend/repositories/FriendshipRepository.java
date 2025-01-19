package com.example.backend.repositories;

import com.example.backend.entities.Friendship;
import com.example.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query("""
                SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
                FROM Friendship f 
                WHERE ((f.user1.id = :userId1 AND f.user2.id = :userId2) 
                   OR (f.user1.id = :userId2 AND f.user2.id = :userId1)) 
                   AND f.isActive = 1
            """)
    boolean existsActiveFriendship(@Param("userId1") int userId1, @Param("userId2") int userId2);

    @Query("""
                SELECT u 
                FROM User u
                JOIN Friendship f ON (f.user1 = u OR f.user2 = u)
                WHERE (f.user1.id = :userId OR f.user2.id = :userId) 
                AND f.isActive = 1
                AND u.id != :userId
            """)
    Page<User> findFriendsByUserId(@Param("userId") int userId, Pageable pageable);
}
