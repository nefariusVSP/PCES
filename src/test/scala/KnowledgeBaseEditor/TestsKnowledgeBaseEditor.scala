package com.pces.Test.KnowledgeBaseEditor

import junit.framework.TestCase
import com.pces.KnowledgeBaseEditor.KnowledgeBaseEditor._

class TestsKnowledgeBaseEditor extends TestCase{

  val login = "user"
  val password = "1234"

  def testUserVerification{
    assert(UserVerification(login, password), true)
  }

}
