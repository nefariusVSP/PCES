package com.pces.KnowledgeBaseEditor
import java.io.{File, PrintWriter}

object KnowledgeBaseEditor {

  //путь к базе данных
  private[this] val UserBDPathFile = "src/main/resources/ProfileExpert.csv"
  //стандартный шаблом
  private[this] val UserBDHead = new String("логин;пароль;\nuser;1234;".getBytes(), "UTF-8")

  def UserVerification (login:String, password:String):Boolean = {
    //провепяем наличие файла, при его отсутствии создается новый.
    //в новом фале есть пользователь по умолчанию UserBDHead.
    val file = new File(UserBDPathFile)
    if (!file.exists()){
      CreateBD(file)
    }
    //читаем базу данных пользователей
    val bufferedSource = io.Source.fromFile(UserBDPathFile)
    val lines = bufferedSource.getLines
    // проверяем есть ли первас строчка
    if(!lines.hasNext) {
      CreateBD(file)
    }
    //прочитываем первую строчку, так как первая строка это заголовок
    lines.next()
    //ищем совпадения на логин и пароль.
    while (lines.hasNext) {
      val cols = lines.next().split(";")
      if (cols.length == 2) {
        if (cols(0) == login && cols(1) == password) {
          bufferedSource.close
          return true
        }
      }
    }
    return false
  }
  // функция создания базы данных
  private[this] def CreateBD(file: File): Unit = {
    file.createNewFile()
    val ios = new PrintWriter(file.getAbsoluteFile)
    ios.print(UserBDHead)
    ios.close()
  }
}

class KnowledgeBaseEditor{

}
