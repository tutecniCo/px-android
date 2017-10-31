package com.mercadopago.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mreverter on 15/1/16.
 */
public class PaymentMethodSearch {

    private List<com.mercadopago.model.PaymentMethodSearchItem> groups;

    @SerializedName("custom_options")
    private List<CustomSearchItem> customSearchItems;

    private List<com.mercadopago.model.PaymentMethod> paymentMethods;

    private List<com.mercadopago.model.Card> cards;

    private AccountMoney accountMoney;

    public List<com.mercadopago.model.PaymentMethodSearchItem> getGroups() {
        return groups;
    }

    public List<com.mercadopago.model.PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public boolean hasSearchItems() {
        return this.groups != null && !this.groups.isEmpty();
    }

    public com.mercadopago.model.PaymentMethod getPaymentMethodBySearchItem(com.mercadopago.model.PaymentMethodSearchItem item) {
        com.mercadopago.model.PaymentMethod requiredPaymentMethod = null;
        if (paymentMethods != null && item != null && item.getId() != null) {
            for (com.mercadopago.model.PaymentMethod currentPaymentMethod : paymentMethods) {
                if (itemMatchesPaymentMethod(item, currentPaymentMethod)) {
                    requiredPaymentMethod = currentPaymentMethod;
                    requiredPaymentMethod.setPaymentTypeId(getPaymentTypeIdFromItem(item, currentPaymentMethod));
                }
            }
        }
        return requiredPaymentMethod;
    }

    private String getPaymentTypeIdFromItem(com.mercadopago.model.PaymentMethodSearchItem item, com.mercadopago.model.PaymentMethod paymentMethod) {
        //Remove payment method id from item id and the splitter
        String paymentType;
        String itemIdWithoutPaymentMethod = item.getId().replaceFirst(paymentMethod.getId(), "");
        if (itemIdWithoutPaymentMethod.isEmpty()) {
            paymentType = paymentMethod.getPaymentTypeId();
        } else {
            paymentType = itemIdWithoutPaymentMethod.substring(1);
        }
        return paymentType;
    }

    private boolean itemMatchesPaymentMethod(com.mercadopago.model.PaymentMethodSearchItem item, com.mercadopago.model.PaymentMethod paymentMethod) {
        return item.getId().startsWith(paymentMethod.getId());
    }

    public com.mercadopago.model.PaymentMethodSearchItem getSearchItemByPaymentMethod(com.mercadopago.model.PaymentMethod selectedPaymentMethod) {
        com.mercadopago.model.PaymentMethodSearchItem requiredItem = null;
        if (selectedPaymentMethod != null) {

            requiredItem = searchItemMatchingPaymentMethod(selectedPaymentMethod);

        }
        return requiredItem;
    }

    private com.mercadopago.model.PaymentMethodSearchItem searchItemMatchingPaymentMethod(com.mercadopago.model.PaymentMethod paymentMethod) {
        return searchItemInList(groups, paymentMethod);
    }

    private com.mercadopago.model.PaymentMethodSearchItem searchItemInList(List<com.mercadopago.model.PaymentMethodSearchItem> list, com.mercadopago.model.PaymentMethod paymentMethod) {
        com.mercadopago.model.PaymentMethodSearchItem requiredItem = null;
        for (com.mercadopago.model.PaymentMethodSearchItem currentItem : list) {

            //Case like "pagofacil", without the payment type in the item id.
            if (itemMatchesPaymentMethod(currentItem, paymentMethod) && currentItem.getId().equals(paymentMethod.getId())) {
                requiredItem = currentItem;
                break;
            }
            //Case like "bancomer_ticket", with the payment type in the item id
            else if (itemMatchesPaymentMethod(currentItem, paymentMethod)) {
                //Remove payment method id from item id
                String potentialPaymentType = currentItem.getId().replaceFirst(paymentMethod.getId(), "");
                if (potentialPaymentType.endsWith(paymentMethod.getPaymentTypeId())) {
                    requiredItem = currentItem;
                    break;
                }
            } else if (currentItem.hasChildren()) {
                requiredItem = searchItemInList(currentItem.getChildren(), paymentMethod);
                if (requiredItem != null) {
                    break;
                }
            }
        }
        return requiredItem;
    }

    public com.mercadopago.model.PaymentMethod getPaymentMethodById(String paymentMethodId) {
        com.mercadopago.model.PaymentMethod foundPaymentMethod = null;
        if (paymentMethods != null) {
            for (PaymentMethod paymentMethod : paymentMethods) {
                if (paymentMethod.getId().equals(paymentMethodId)) {
                    foundPaymentMethod = paymentMethod;
                    break;
                }
            }
        }
        return foundPaymentMethod;
    }

    public com.mercadopago.model.Card getCardById(String cardId) {
        com.mercadopago.model.Card foundCard = null;
        if (cards != null) {
            for (com.mercadopago.model.Card card : cards) {
                if (card.getId().equals(cardId)) {
                    foundCard = card;
                    break;
                }
            }
        }
        return foundCard;
    }

    public List<CustomSearchItem> getCustomSearchItems() {
        return customSearchItems;
    }

    public boolean hasCustomSearchItems() {
        return customSearchItems != null && !customSearchItems.isEmpty();
    }

    public List<com.mercadopago.model.Card> getCards() {
        return cards;
    }

    public AccountMoney getAccountMoney() {
        return accountMoney;
    }

    public boolean hasSavedCards() {
        return cards != null && !cards.isEmpty();
    }

    public void setCards(List<com.mercadopago.model.Card> cards, String lastFourDigitsText) {
        if (cards != null) {
            this.customSearchItems = new ArrayList<>();
            this.cards = new ArrayList<>();

            for (Card card : cards) {
                CustomSearchItem searchItem = new CustomSearchItem();
                searchItem.setDescription(lastFourDigitsText + " " + card.getLastFourDigits());
                searchItem.setType(card.getPaymentMethod().getPaymentTypeId());
                searchItem.setId(card.getId());
                searchItem.setPaymentMethodId(card.getPaymentMethod().getId());
                this.customSearchItems.add(searchItem);
                this.cards.add(card);
            }
        }
    }

    public void setGroups(List<PaymentMethodSearchItem> groups) {
        this.groups = groups;
    }

    public void setCustomSearchItems(List<CustomSearchItem> customSearchItems) {
        this.customSearchItems = customSearchItems;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setAccountMoney(AccountMoney accountMoney) {
        this.accountMoney = accountMoney;
    }
}
