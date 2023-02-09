package com.company.ProjectSpring.service;

import com.company.ProjectSpring.models.Appeal;
import com.company.ProjectSpring.models.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
public class HtmlLetter {


    public static String getLetterTextZNP(User user, Appeal appeal) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateAppeal = simpleDateFormat.format(appeal.getDateAppeal());
        String dateAnswer = simpleDateFormat.format(appeal.getDateAnswer());
        String result = "\t<table align=\"center\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\" style=\"padding-top:40px\">\n" +
                "\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:88%;font-family:Arial,sans-serif;font-size:16px;line-height:24px;color:#535b63\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:32px;font-size:24px;line-height:28px;color:#000\">Здравствуйте, <b>" + user.getFirstName() + "</b> <b>" + user.getPatronymicName() + "</b>!</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:40px\">" + dateAppeal + " Вы записались на прием.</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:24px\">\n" +
                "\t\t\t\t\t\t\t\t<table bgcolor=\"#F4F9FF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:24px;padding-bottom:24px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:88%;font-family:Arial,sans-serif;font-size:15px;line-height:20px;color:#535b63\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">Структурное подразделение — <b style=\"color:#000\">" + appeal.getService().getDepartment().getName() + "</b></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:10px\">Услуга — <b style=\"color:#000\">" + appeal.getService().getServiceName() + "</b></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:10px\">Время приема - <b style=\"color:#000\">" + dateAnswer + "</b></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%;font-family:Arial,sans-serif;font-size:16px;line-height:24px;color:#535b63\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:40px\">\n" +
                "\t\t\t\t\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table bgcolor=\"#F6F6F6\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background:url(https://ci3.googleusercontent.com/proxy/4hDgzrt_OyyPfnMdE8aUa8XTEu56MeMcighOO5RXEmxe8WXhmdnIXtFPt178Z9wDyuXRhO8Oyf8BwErnA0I=s0-d-e1-ft#https://gu-st.ru/content/mail/shadow_up.png) 0 0 repeat-x;background-size:contain;width:100%;height:10px\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:15px;padding-bottom:15px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"width:76px;vertical-align:top\"><img class=\"CToWUd\" height=\"56\" src=\"https://ci4.googleusercontent.com/proxy/0sh1U8G6OYgdr9ob5eh99-PCeE8kyxl8Jjlm-kLzRrsshRrdZGbbPrnJwgiag0Qt7655Iuf-cv8TvY8vt4vtNu-CoRVi=s0-d-e1-ft#https://gu-st.ru/content/mail/VICH/all_be_warn.png\" style=\"display:block;border:none\" width=\"56\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tНе забудьте придти заранее.<br> \nАдрес структурного подразделения " + appeal.getService().getDepartment().getAddress() + "</a>.\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background:url(https://ci4.googleusercontent.com/proxy/Pt2GVrpYTmIi_ZNMCUTcCjPC4KYiA0GM0y0G00Qf3MkQF3wU2y8rt4C7srdYj7wYFh4tRgNidfToUBpBOLDCtQ=s0-d-e1-ft#https://gu-st.ru/content/mail/shadow_down.png) 0 0 repeat-x;background-size:contain;width:100%;height:10px\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>";
        return result;
    }

    public static String getLetterTextOnline(User user, Appeal appeal) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateAppeal = simpleDateFormat.format(appeal.getDateAppeal());
        String result = "\t<table align=\"center\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\" style=\"padding-top:40px\">\n" +
                "\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:88%;font-family:Arial,sans-serif;font-size:16px;line-height:24px;color:#535b63\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:32px;font-size:24px;line-height:28px;color:#000\">Здравствуйте, <b>" + user.getFirstName() + "</b> <b>" + user.getPatronymicName() + "</b>!</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:40px\">" + dateAppeal + " Вы подали заявку на получение онлайн услуги.</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:24px\">\n" +
                "\t\t\t\t\t\t\t\t<table bgcolor=\"#F4F9FF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:24px;padding-bottom:24px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:88%;font-family:Arial,sans-serif;font-size:15px;line-height:20px;color:#535b63\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">Структурное подразделение — <b style=\"color:#000\">" + appeal.getService().getDepartment().getName() + "</b></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:10px\">Услуга — <b style=\"color:#000\">" + appeal.getService().getServiceName() + "</b></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%;font-family:Arial,sans-serif;font-size:16px;line-height:24px;color:#535b63\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:40px\">\n" +
                "\t\t\t\t\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table bgcolor=\"#F6F6F6\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background:url(https://ci3.googleusercontent.com/proxy/4hDgzrt_OyyPfnMdE8aUa8XTEu56MeMcighOO5RXEmxe8WXhmdnIXtFPt178Z9wDyuXRhO8Oyf8BwErnA0I=s0-d-e1-ft#https://gu-st.ru/content/mail/shadow_up.png) 0 0 repeat-x;background-size:contain;width:100%;height:10px\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:15px;padding-bottom:15px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"width:76px;vertical-align:top\"><img class=\"CToWUd\" height=\"56\" src=\"https://ci4.googleusercontent.com/proxy/0sh1U8G6OYgdr9ob5eh99-PCeE8kyxl8Jjlm-kLzRrsshRrdZGbbPrnJwgiag0Qt7655Iuf-cv8TvY8vt4vtNu-CoRVi=s0-d-e1-ft#https://gu-st.ru/content/mail/VICH/all_be_warn.png\" style=\"display:block;border:none\" width=\"56\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tАдрес структурного подразделения: " + appeal.getService().getDepartment().getAddress() + ".\n <br>Телефон: " + appeal.getService().getDepartment().getNumber() + "</a>.\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background:url(https://ci4.googleusercontent.com/proxy/Pt2GVrpYTmIi_ZNMCUTcCjPC4KYiA0GM0y0G00Qf3MkQF3wU2y8rt4C7srdYj7wYFh4tRgNidfToUBpBOLDCtQ=s0-d-e1-ft#https://gu-st.ru/content/mail/shadow_down.png) 0 0 repeat-x;background-size:contain;width:100%;height:10px\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>";
        return result;
    }

    public static String getLetterTextManager(Appeal appeal) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateAppeal = simpleDateFormat.format(appeal.getDateAppeal());
        String result = "\t<table align=\"center\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\" style=\"padding-top:40px\">\n" +
                "\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:88%;font-family:Arial,sans-serif;font-size:16px;line-height:24px;color:#535b63\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:32px;font-size:24px;line-height:28px;color:#000\">Здравствуйте, <b>" + appeal.getUser().getFirstName() + "</b> <b>" + appeal.getUser().getPatronymicName() + "</b>!</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:40px\">" + dateAppeal + " Вы подавали заявку на получение онлайн услуги. Специалисты подразделения ответили на Ваш запрос. <br> Зайдите в личный кабинет, чтобы прочитать его.</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:24px\">\n" +
                "\t\t\t\t\t\t\t\t<table bgcolor=\"#F4F9FF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:24px;padding-bottom:24px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:88%;font-family:Arial,sans-serif;font-size:15px;line-height:20px;color:#535b63\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">Структурное подразделение — <b style=\"color:#000\">" + appeal.getService().getDepartment().getName() + "</b></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:10px\">Услуга — <b style=\"color:#000\">" + appeal.getService().getServiceName() + "</b></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%;font-family:Arial,sans-serif;font-size:16px;line-height:24px;color:#535b63\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-bottom:40px\">\n" +
                "\t\t\t\t\t\t\t\t<table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table bgcolor=\"#F6F6F6\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:99%\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background:url(https://ci3.googleusercontent.com/proxy/4hDgzrt_OyyPfnMdE8aUa8XTEu56MeMcighOO5RXEmxe8WXhmdnIXtFPt178Z9wDyuXRhO8Oyf8BwErnA0I=s0-d-e1-ft#https://gu-st.ru/content/mail/shadow_up.png) 0 0 repeat-x;background-size:contain;width:100%;height:10px\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"padding-top:15px;padding-bottom:15px\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"width:76px;vertical-align:top\"><img class=\"CToWUd\" height=\"56\" src=\"https://ci4.googleusercontent.com/proxy/0sh1U8G6OYgdr9ob5eh99-PCeE8kyxl8Jjlm-kLzRrsshRrdZGbbPrnJwgiag0Qt7655Iuf-cv8TvY8vt4vtNu-CoRVi=s0-d-e1-ft#https://gu-st.ru/content/mail/VICH/all_be_warn.png\" style=\"display:block;border:none\" width=\"56\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tАдрес структурного подразделения: " + appeal.getService().getDepartment().getAddress() + ".<br>Телефон: " + appeal.getService().getDepartment().getNumber() + "</a>.\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background:url(https://ci4.googleusercontent.com/proxy/Pt2GVrpYTmIi_ZNMCUTcCjPC4KYiA0GM0y0G00Qf3MkQF3wU2y8rt4C7srdYj7wYFh4tRgNidfToUBpBOLDCtQ=s0-d-e1-ft#https://gu-st.ru/content/mail/shadow_down.png) 0 0 repeat-x;background-size:contain;width:100%;height:10px\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>";
        return result;
    }
}
