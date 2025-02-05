import {configure, defineRule} from 'vee-validate';
import {all} from '@vee-validate/rules';
import {localize, setLocale} from '@vee-validate/i18n';
import ru from '@vee-validate/i18n/dist/locale/ru.json';

export default {
    install(Vue, opts) {
        Object.keys(all).forEach(rule => {
            defineRule(rule, all[rule]);
        });

        defineRule('confirmed', (value, [target], ctx) => {
            if (value === ctx.form[target]) {
                return true;
            }
            return 'Пароли не совпадают';
        });

        configure({
            generateMessage: localize({
                ru
            })
        });
        setLocale("ru");
    }
}