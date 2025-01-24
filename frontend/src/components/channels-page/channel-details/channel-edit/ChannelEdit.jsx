import { useContext, useEffect, useState } from "react";
import {
    Button,
    FormControl,
    FormLabel,
    Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Text,
    useToast,
} from "@chakra-ui/react";

import * as channelService from "../../../../services/channelService";
import AuthContext from "../../../../contexts/authContext";

export default function ChannelEdit({
    isOpen,
    onClose,
    channel,
    fetchChannel,
}) {
    const [values, setValues] = useState({
        name: channel.name,
    });
    const [errors, setErrors] = useState({
        name: "",
    });
    const { id: userId } = useContext(AuthContext);
    const toast = useToast();

    const onChange = (e) => {
        setValues((state) => ({
            ...state,
            [e.target.name]: e.target.value,
        }));
    };

    const validateForm = (currentChannel) => {
        const newErrors = {
            name: "",
        };
        if (!currentChannel.name.trim()) {
            newErrors.name = "Name can't be empty";
        }
        setErrors(newErrors);

        return !newErrors.name;
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        setErrors({
            name: "",
        });
        const updatedChannel = {
            name: values.name,
        };
        if (!validateForm(updatedChannel)) {
            return;
        }

        try {
            await channelService.update(channel.id, userId, updatedChannel);
            toast({
                title: "Channel updated successfully!",
                status: "success",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
            fetchChannel();
            onCloseForm();
        } catch (error) {
            toast({
                title: error.message || "Channel could not be updated!",
                status: "error",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
        }
    };

    const onCloseForm = () => {
        onClose();
    };

    return (
        <Modal isOpen={isOpen} onClose={onCloseForm}>
            <ModalOverlay />
            <ModalContent mx={{ base: "4", md: "0" }}>
                <ModalHeader>Update channel</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <form onSubmit={onSubmit}>
                        <FormControl mb={4} isRequired>
                            <FormLabel>Name</FormLabel>
                            <Input
                                type="text"
                                name="name"
                                value={values.name}
                                onChange={onChange}
                                placeholder="Name"
                            />
                            {errors.name && (
                                <Text color="red.500" fontSize="sm">
                                    {errors.name}
                                </Text>
                            )}
                        </FormControl>
                    </form>
                </ModalBody>
                <ModalFooter>
                    <Button variant="primary" mr={3} onClick={onSubmit}>
                        Update
                    </Button>
                    <Button onClick={onCloseForm}>Cancel</Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
}
