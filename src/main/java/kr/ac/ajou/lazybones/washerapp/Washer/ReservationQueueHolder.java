package kr.ac.ajou.lazybones.washerapp.Washer;

/**
* kr/ac/ajou/lazybones/washerapp/Washer/ReservationQueueHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from washer.idl
* 2015년 6월 15일 월요일 오후 4시 53분 51초 KST
*/

public final class ReservationQueueHolder implements org.omg.CORBA.portable.Streamable
{
  public kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueue value = null;

  public ReservationQueueHolder ()
  {
  }

  public ReservationQueueHolder (kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueue initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueueHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueueHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueueHelper.type ();
  }

}
