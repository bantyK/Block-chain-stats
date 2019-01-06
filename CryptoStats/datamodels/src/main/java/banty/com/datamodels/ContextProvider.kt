package banty.com.datamodels

import android.content.Context

/**
 * Created by Banty on 07/01/19.
 */
class ContextProvider {
    companion object {
        private lateinit var context: Context

        public fun setContext(context: Context) {
            this.context = context
        }

        public fun getContext(): Context {
            return if (context != null) {
                context
            } else {
                throw IllegalStateException("Context is not set in context provider. Set context first")
            }
        }

    }
}