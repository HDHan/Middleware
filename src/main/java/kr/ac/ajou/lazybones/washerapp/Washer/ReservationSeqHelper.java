package kr.ac.ajou.lazybones.washerapp.Washer;


/**
* kr/ac/ajou/lazybones/washerapp/Washer/ReservationSeqHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from washer.idl
* 2015년 6월 2일 화요일 오후 4시 16분 02초 KST
*/

abstract public class ReservationSeqHelper
{
  private static String  _id = "IDL:Washer/ReservationSeq:1.0";

  public static void insert (org.omg.CORBA.Any a, kr.ac.ajou.lazybones.washerapp.Washer.Reservation[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static kr.ac.ajou.lazybones.washerapp.Washer.Reservation[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = kr.ac.ajou.lazybones.washerapp.Washer.ReservationHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (kr.ac.ajou.lazybones.washerapp.Washer.ReservationSeqHelper.id (), "ReservationSeq", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static kr.ac.ajou.lazybones.washerapp.Washer.Reservation[] read (org.omg.CORBA.portable.InputStream istream)
  {
    kr.ac.ajou.lazybones.washerapp.Washer.Reservation value[] = null;
    int _len0 = istream.read_long ();
    value = new kr.ac.ajou.lazybones.washerapp.Washer.Reservation[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = kr.ac.ajou.lazybones.washerapp.Washer.ReservationHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, kr.ac.ajou.lazybones.washerapp.Washer.Reservation[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      kr.ac.ajou.lazybones.washerapp.Washer.ReservationHelper.write (ostream, value[_i0]);
  }

}
