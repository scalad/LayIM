package com.silence.repository

import com.silence.enties.User
import org.springframework.data.jpa.repository.JpaRepository
import java.lang.Long

/**
 * @description User Dao
 * @date 2017年1月11日
 * @author wang
 *
 */
trait UserRepository extends JpaRepository[User, Long]