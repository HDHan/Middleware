package kr.ac.ajou.lazybones.washerapp.Washer;


/**
* kr/ac/ajou/lazybones/washerapp/Washer/Reservation.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from washer.idl
* 2015년 6월 2일 화요일 오후 4시 16분 02초 KST
*/

public final class Reservation implements org.omg.CORBA.portable.IDLEntity
{
  public String who = null;
  public String duration = null;

  public Reservation ()
  {
  } // ctor

  public Reservation (String _who, String _duration)
  {
    who = _who;
    duration = _duration;
  } // ctor

} // class Reservation
