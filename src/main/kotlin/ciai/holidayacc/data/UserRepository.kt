package ciai.holidayacc.data


import ciai.holidayacc.domain.UserDAO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserDAO, String> {

    @Query("SELECT u " +
            "FROM UserDAO u " +
            "WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(concat(:name,'%'))) " +
            "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(concat(:email,'%'))) " +
            "AND (:phone IS NULL OR LOWER(u.phone) LIKE LOWER(concat(:phone,'%')))")
    fun searchUsers(name: String?, email: String?, phone: String?, pageable: Pageable
    ): Page<UserDAO>

}