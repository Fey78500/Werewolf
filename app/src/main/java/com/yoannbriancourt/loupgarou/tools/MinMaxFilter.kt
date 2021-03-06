import android.text.InputFilter
import android.text.Spanned

/**
 * Created by npatel on 4/5/2016.
 */
class MinMaxFilter : InputFilter {

    private var mIntMin: Int = 0
    private var mIntMax: Int = 0

    constructor(minValue: Int, maxValue: Int) {
        this.mIntMin = minValue
        this.mIntMax = maxValue
    }

    constructor(minValue: String, maxValue: String) {
        this.mIntMin = Integer.parseInt(minValue)
        this.mIntMax = Integer.parseInt(maxValue)
    }

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        try {
            val input = Integer.parseInt(dest.toString() + source.toString())
            if (isInRange(mIntMin, mIntMax, input))
                return null
        } catch (nfe: NumberFormatException) {
        }

        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}