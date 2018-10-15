package fyi.jackson.activejournal.dummy;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class Data {

    // THESE STRINGS ARE NOT IN XML BECAUSE THE WILL NOT NEED TO BE TRANSLATED

    public static List<Point> getClingmansDome() {
        return new ArrayList<Point>() {{
            String encodedPositions = "{ijxEx`e|NwAhJfAzKT^ZPh@h@Z`@^d@Rf@Dl@?`A?f@C^@@Eh@Af@HRA`@Ah@EPC\\Gl@Ud@?NKRGHYJSJ]Dc@?SNMT?HU^MZERMVALEBC@D??@C?C@A??B?@?????????????CB?@E@ADPLl@NTDPB`@JPEh@?d@FVFNPNFLRPPNNVH\\BVDb@H^BLHXJ^?d@Gv@If@Cp@C^AXG|BBJ?A??????EA?SCA?NBV@b@Ad@BRCD@NIJGZOb@ORQZOVAPMd@IVUZULEHITD^RZNn@Lf@?ZHj@IXAb@YTBX@AIT?f@E^Nj@NJPZR`@NLJTNJ^P^CLZ^HAt@Lb@?PC`@Ih@@b@Bp@BVJb@TJHND\\D^C^Q^M^GLWb@Sb@AXKf@FB\\e@PMVSLGXM\\Kf@]TEVMLU^YJH?AA@@?DE^Od@]r@Mf@If@R`@Kn@?DrANp@Jl@Ln@Zb@ZTp@B`@Fl@MZNp@GR\\f@VLd@Tb@L|@Ph@ZRd@`@h@PPVVb@Pj@b@^j@`@n@MV?l@Ad@Ab@ZJb@h@Z\\b@ZZh@F^X\\\\Xn@Jf@Dl@Tl@T^d@Zd@^b@NXZd@Ad@Cd@KVTj@Od@L\\Nf@Jd@Ld@?d@Jd@?`@Jh@INB?MVHf@UX?^@f@?b@RZXPh@d@XPPTl@Vh@Xj@Rn@Jd@\\b@Lj@Hb@Hp@Bh@?p@Br@Nh@V\\T\\f@NP`@X`@Rd@Nn@Bl@Gl@Qf@?^Qp@E|@E@ACCAMHE^Sl@At@@v@Gh@?h@Eh@@b@Fj@Hp@Fd@BNTx@V^Xb@b@HXd@b@Fd@@h@EZQd@?ZO`@]XOXQ\\O`@[\\UZY`@W`@Mb@Fd@J\\VZRPh@Pd@Ld@Jr@Fl@Db@Bf@@n@@l@@p@Dh@Hj@@d@Lp@D\\Nn@Fb@Ll@Hr@?XNj@Jf@Pb@L`@A@CC?CAB?@??DAACBA?@????C@????A@?CA@@A@A??A???B?????A??@??@????AA?B@??@A@???C?????BABCE?A@????@@ACBDEFE@HN?FRTLb@Pd@Hf@Ht@Br@?p@Bp@@f@@f@Bv@Fh@Jd@Ld@Ht@Xf@\\d@\\Tb@Zh@Bj@@b@@f@G`@O\\Od@Qf@SZa@d@Ub@[d@o@^?h@Ph@NXd@LHFVV`@Xb@`@j@PNl@`@d@ZRZXRb@Bb@\\^b@Xf@Nr@H|@Nj@Xf@Xp@TJb@\\h@VDBCIH?@DRTAFNLBRJ`AFp@Hb@Fj@Fr@Jx@B\\HXFl@Nr@FLLd@Nb@Nh@Pj@X|@Tb@Vj@Vv@VPd@Pb@^j@J^FXCl@Et@?j@Ub@K`@K^Sb@@p@PR`@Vv@P\\\\l@f@f@\\`@`@b@\\^NNRVh@f@XT^Z`@\\n@Tn@L^RZPh@Lj@Fj@@d@Fj@?b@Dj@@r@Dh@T\\Lh@\\VVd@Vf@Pj@L^Bf@Ab@Gl@M`@O\\O`@Yj@LZDP`@Rb@Zf@`@\\Z^\\LTXj@TZ`@b@P\\Rd@P^Jb@Bd@A\\Bb@I\\IX?TECXTs@Ze@\\]ZYFBLEVU`@C^Eb@s@^a@f@?d@JZGHd@Jj@Nl@Jf@Ap@Rx@Tb@Zh@\\XXVf@J`@Jd@PZTX^Nj@Dt@@`@Cp@At@QXDt@Fv@B`@Bh@Hp@Jf@DRP`ADh@?l@B\\?l@A|@@p@Kn@Ip@@b@Gp@C`@An@E\\Bj@Kr@?f@Gh@Dd@@R@DCn@Ch@Dj@Lb@Er@Gf@Jj@Hb@f@LCBM?FAAAB??@?A?@????????????ID@f@Rd@Xb@Jb@l@Ph@Tj@B^?\\IZEXA`@K^Gh@Gb@OZK^Gh@M^Q\\I`@M`@Ib@OPGVEl@GZGb@Ff@JXXf@FZP`@L?@??AJADhACpABt@GPATI^Mb@K\\Ah@Id@Ih@Jd@H`@X`@DM?DC@FNt@Vd@^l@b@XR\\Dh@Bj@C`@[f@W^EN_@d@Cf@QH_@I??c@DMAe@G[C_@AYCa@G_@EW@OBYBUHUCUBMICA?@???????@S@[HYV]LSNQHU?OD[ZQNYXGb@Q\\OXEBQNQj@ONMXWRIH[d@SZIDGNQHOFKVOXCPSZUb@QVSPOJWHSN]HOOW_@Yi@O_@Bg@Bc@BOPe@XUNZEj@U@IYIUA?";

            long startTimestamp = 1512400212784L;
            long timeIncrement = 16000L;
            int startIndex = 1234;
            int accuracy = 5;

            List<LatLng> latLngs = PolyUtil.decode(encodedPositions);

            long timestamp = startTimestamp;
            int index = startIndex;
            for (LatLng latLng : latLngs) {
                add(new Point(index, timestamp, latLng.latitude, latLng.longitude, accuracy, null, null));
                timestamp += timeIncrement;
                index++;
            }
        }};
    }

    public static List<Point> getSpinnakerSailing() {
        return new ArrayList<Point>() {{
            String encodedPositions = "{uebGxwzxN??zDoRDw@D_AFw@N{ALo@Ho@Ho@RkARyALyAJ}ATuA^sAZqA\\wAVwAH}ALiBCaB?kA?gA@kAHkADeANqAPuALyALyAZuAVsAZyAVuARsAZsA^iAb@iAf@w@f@m@b@k@\\c@`@o@\\e@V_@ZYT[Re@VGATO\\B^L`@NZLn@Td@Xd@T^Th@PT@[Ho@Li@Zw@^o@Zq@`@q@\\i@Zm@`@k@d@s@`@k@@o@c@QU]YYW[SWSWUWUQOWUUU[Y[UY]c@UW[]]U[]g@W_@[YY]Ye@[[[]Ua@S[UuF{DgFHs@WQQUGWK[KSMSIKGKIKIMCICEEKImAk@wBy@_@SOIuB}@gCaAcAa@UEqD{AiAa@cBa@eDqAa@OsCaAoFyBk@OSMMEMIUMQISGUKMEUCMIMGMKMGMGMEMGSIQEAJC\\HTEh@Bp@Hn@Lr@Lp@Hv@Lz@TfAJfALt@NdANfAPbANlAN`ARhATfAPlARdATbAVjAr@vCXhAZfAZhAZnA\\hA\\bA\\jA\\dAZlAZbAb@hA\\hA^fA^z@\\t@Vp@x@hB\\r@^v@\\t@^n@^p@Vp@\\n@Zj@Vl@Zp@\\n@\\l@Tn@Zj@Tn@\\v@Zl@Xj@Zn@Xj@Zp@Vf@Hj@Nn@Pv@Vz@Tl@P`@N^JZJVH`@d@h@r@v@t@~@t@|@p@~@nC`IkEf\\";


            long startTimestamp = 1536103750440L;
            long timeIncrement = 16000L;
            int startIndex = 2154;
            int accuracy = 5;

            List<LatLng> latLngs = PolyUtil.decode(encodedPositions);

            long timestamp = startTimestamp;
            int index = startIndex;
            for (LatLng latLng : latLngs) {
                add(new Point(index, timestamp, latLng.latitude, latLng.longitude, accuracy, null, null));
                timestamp += timeIncrement;
                index++;
            }
        }};
    }
}
